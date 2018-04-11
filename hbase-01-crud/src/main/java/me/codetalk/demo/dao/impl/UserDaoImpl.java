package me.codetalk.demo.dao.impl;

import me.codetalk.demo.dao.UserDao;
import me.codetalk.demo.entity.User;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserDaoImpl extends AbstractBaseDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User getUser(String userId) throws IOException {
        Connection conn = null;
        Table usersTable = null;

        try {
            conn = getConnection();
            usersTable = conn.getTable(TableName.valueOf(TABLE_NAME));

            Get g = new Get(Bytes.toBytes(userId));
            g.addFamily(CF_INFO);

            Result r = usersTable.get(g);
            if (r.isEmpty()) {
                LOGGER.info(String.format("User %s not found", userId));

                return null;
            } else {
                return resultAsUser(userId, r);
            }
        } finally {
            closeTableAndConn(usersTable, conn);
        }
    }

    @Override
    public User deleteUser(String userId) throws IOException {
        return null;
    }

    private User resultAsUser(String userId, Result r) {
        byte[] twBytes = r.getValue(CF_INFO, COL_TWCOUNT);

        return new User(userId,
                Bytes.toString(r.getValue(CF_INFO, COL_NAME)),
                Bytes.toString(r.getValue(CF_INFO, COL_EMAIL)),
                twBytes == null ? 0L : Bytes.toLong(twBytes));

    }

}
