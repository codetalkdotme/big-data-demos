package me.codetalk.demo.dao.impl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public abstract class AbstractBaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseDao.class);

    @Value("${hbase.zookeeper.quorum}")
    private String hbaseZkQuorum;

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
