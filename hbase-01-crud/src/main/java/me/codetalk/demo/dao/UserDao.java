package me.codetalk.demo.dao;

import me.codetalk.demo.entity.User;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public interface UserDao {

    String TABLE_USERS_STR = "users";

    byte[] TABLE_USERS = Bytes.toBytes("users");

    byte[] CF_INFO = Bytes.toBytes("info");

    byte[] COL_NAME = Bytes.toBytes("name");
    byte[] COL_EMAIL = Bytes.toBytes("email");
    byte[] COL_TWCOUNT = Bytes.toBytes("twit_count");

    /**
     *
     * @param userId
     * @return null if not found
     * @throws IOException
     */
    User getUser(String userId);

    /**
     *
     * @param userId
     * @return
     * @throws IOException
     */
    void deleteUser(String userId);

    /**
     *
     * @param user
     * @throws IOException
     */
    void putUser(User user);

    /**
     *
     * @param userId
     * @return twit count after incr
     */
    Long incrTwitCount(String userId);

    /**
     *
     * @param startId
     * @param stopId
     * @return
     */
    @Deprecated
    List<User> scanUsers(String startId, String stopId);

}
