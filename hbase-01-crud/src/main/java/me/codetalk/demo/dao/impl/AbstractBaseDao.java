package me.codetalk.demo.dao.impl;

import me.codetalk.demo.dao.exception.BaseDaoException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseDao<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseDao.class);

    @Value("${hbase.zookeeper.quorum}")
    private String hbaseZkQuorum;

    /**
     *
     * @param r
     * @return
     */
    protected abstract T resultToEntity(Result r);

    /**
     *
     * @param entity
     * @return null if not found
     */
    protected abstract Put entityToPut(T entity);

    protected T get(byte[] tbl, Get g) {
        Connection conn = null;
        Table table = null;

        try {
            conn = getConnection();
            table = conn.getTable(TableName.valueOf(tbl));

            Result r = table.get(g);

            return r.isEmpty() ? null : resultToEntity(r);
        } catch(IOException ex) {
            rethrow(ex);

            return null;
        } finally {
            closeTableAndConn(table, conn);
        }
    }

    protected List<T> scan(byte[] tbl, Scan s) {
        Connection conn = null;
        Table table = null;

        try {
            conn = getConnection();
            table = conn.getTable(TableName.valueOf(tbl));

            ResultScanner results = table.getScanner(s);
            List<T> list = new ArrayList<T>();
            for(Result r : results) {
                list.add(resultToEntity(r));
            }

            return list;
        } catch(IOException ex) {
            rethrow(ex);

            return null;
        } finally {
            closeTableAndConn(table, conn);
        }
    }

    protected void put(byte[] tbl, T entity) {
        Connection conn = null;
        Table table = null;

        try {
            conn = getConnection();
            table = conn.getTable(TableName.valueOf(tbl));

            Put p = entityToPut(entity);
            table.put(p);                   // 此时其他会话可以看到数据, 无需关闭session
        } catch(IOException ex) {
            rethrow(ex);
        } finally {
            closeTableAndConn(table, conn);
        }
    }

    protected void delete(byte[] tbl, byte[] rowkey) {
        Connection conn = null;
        Table table = null;

        try {
            conn = getConnection();
            table = conn.getTable(TableName.valueOf(tbl));

            Delete d = new Delete(rowkey);
            table.delete(d);
        } catch(IOException ex) {
            rethrow(ex);
        } finally {
            closeTableAndConn(table, conn);
        }
    }

    protected Long incr(byte[] tbl, byte[] rowkey, byte[] cf, byte[] col, Long delta) {
        Connection conn = null;
        Table table = null;

        try {
            conn = getConnection();
            table = conn.getTable(TableName.valueOf(tbl));

            return table.incrementColumnValue(rowkey, cf, col, delta);
        } catch(IOException ex) {
            rethrow(ex);

            return null;
        } finally {
            closeTableAndConn(table, conn);
        }
    }

    @Bean
    public Configuration hbaseConfig() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", hbaseZkQuorum);

        return conf;
    }

    public Connection getConnection() throws IOException {
        LOGGER.info("In getConnection...");

        return ConnectionFactory.createConnection(hbaseConfig());
    }

    private void rethrow(IOException ex) {
        BaseDaoException daoException = new BaseDaoException(ex);

        throw daoException;
    }

    protected void closeTableAndConn(Table tbl, Connection conn) {
        closeTable(tbl);
        closeConn(conn);
    }

    protected void closeTable(Table tbl) {
        if(tbl == null) return;

        try {
            tbl.close();
        } catch(IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    protected void  closeConn(Connection conn) {
        if(conn == null) return;

        try {
            conn.close();
        } catch(IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
