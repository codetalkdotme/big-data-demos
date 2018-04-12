package me.codetalk.demo.main;

import me.codetalk.demo.dao.UserDao;
import me.codetalk.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan({
        "me.codetalk.demo.dao"
})

/**
 * TODO
 * 1: 每次获取connection都会建立ZK session, 应该重用
 *
 */
public class HbaseCrudApp implements CommandLineRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(HbaseCrudApp.class);

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(HbaseCrudApp.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user1 = new User("Ted", "Guobiao Xu", "gbxu@outlook.com", 106L);

        LOGGER.info(String.format("Hbase - Put user: [%s]", user1.toString()));
        userDao.putUser(user1);

        LOGGER.info(String.format("Hbase - Incr twit count: [%s]", user1.getUserId()));
        userDao.incrTwitCount(user1.getUserId());

        LOGGER.info(String.format("Hbase - Get user: [%s]", user1.getUserId()));
        User userInDb = userDao.getUser("Ted");
        LOGGER.info(String.format("Hbase - User fetched: [%s]", userInDb.getUserId()));

        User user2 = new User("guobxu", "Ted Xu", "codetalkdotme@gmail.com", 92L);
        LOGGER.info(String.format("Hbase - Put user: [%s]", user2.toString()));
        userDao.putUser(user2);

        String startId = "000", stopId = "zzz";
        LOGGER.info(String.format("Hbase - Scan users: start=[%s], stop=[%s]", startId, stopId));
        List<User> userList = userDao.scanUsers(startId, stopId);
        LOGGER.info(String.format("Hbase - Scan results: %s", userList));
    }

}
