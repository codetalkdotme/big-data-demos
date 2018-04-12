package me.codetalk.demo.dao.impl;

import me.codetalk.demo.dao.UserDao;
import me.codetalk.demo.entity.User;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User getUser(String userId) {
        LOGGER.info(String.format("Hbase: [GET] Table [%s] Rowkey [%s]", TABLE_USERS_STR, userId));

        Get g = new Get(Bytes.toBytes(userId));
        g.addFamily(CF_INFO);

        return get(TABLE_USERS, g);
    }

    @Override
    public void deleteUser(String userId) {
        LOGGER.info(String.format("Hbase: [DELETE] Table [%s] Rowkey [%s]", TABLE_USERS_STR, userId));

        delete(TABLE_USERS, Bytes.toBytes(userId));
    }

    @Override
    public void putUser(User user) {
        LOGGER.info(String.format("Hbase: [PUT] Table [%s] Rowkey [%s]", TABLE_USERS_STR, user.getUserId()));

        put(TABLE_USERS, user);
    }

    @Override
    public Long incrTwitCount(String userId) {
        return incr(TABLE_USERS, Bytes.toBytes(userId), CF_INFO, COL_TWCOUNT, 1L);
    }

    @Override
    public List<User> scanUsers(String startId, String stopId) {
        Scan s = new Scan();
        s.setStartRow(Bytes.toBytes(startId));
        s.setStopRow(Bytes.toBytes(stopId));
        s.addFamily(CF_INFO);

        return scan(TABLE_USERS, s);
    }

    @Override
    protected User resultToEntity(Result r) {
        byte[] twBytes = r.getValue(CF_INFO, COL_TWCOUNT);

        return new User(Bytes.toString(r.getRow()),
                Bytes.toString(r.getValue(CF_INFO, COL_NAME)),
                Bytes.toString(r.getValue(CF_INFO, COL_EMAIL)),
                twBytes == null ? 0L : Bytes.toLong(twBytes));
    }

    @Override
    protected Put entityToPut(User entity) {
        Put p = new Put(Bytes.toBytes(entity.getUserId()));

        p.addColumn(CF_INFO, COL_NAME, Bytes.toBytes(entity.getName()));
        p.addColumn(CF_INFO, COL_EMAIL, Bytes.toBytes(entity.getEmail()));

        Long twCount = entity.getTwitCount();
        p.addColumn(CF_INFO, COL_TWCOUNT, Bytes.toBytes(twCount == null ? 0L : twCount));

        return p;
    }

}
