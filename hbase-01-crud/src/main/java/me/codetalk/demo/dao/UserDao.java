package me.codetalk.demo.dao;

import me.codetalk.demo.entity.User;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public interface UserDao {

    byte[] TABLE_NAME = Bytes.toBytes("users");
    byte[] CF_INFO = Bytes.toBytes("info");

    byte[] COL_NAME = Bytes.toBytes("name");
    byte[] COL_EMAIL = Bytes.toBytes("email");
    byte[] COL_TWCOUNT = Bytes.toBytes("twit_count");

    /**
     *
     * @param userId
     * @return
     * @throws IOException
     */
    User getUser(String userId) throws IOException;

    /**
     *
     * @param userId
     * @return
     * @throws IOException
     */
    User deleteUser(String userId) throws IOException;

}
