package com.phicomm.demo;

import com.phicomm.demo.messaging.IKafkaMesgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class KafkaTopicTest implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicTest.class);

    public static final String SAMPLE_DATA_FILE = "brokerstatus_short.log";

//    @Value("${topic.name}")
    public static final String TOPIC_STAT_LOG = "topic-mqtt-stat-log-spark";

//    @Value("${consumer.groupId}")
    public static final String CONSUMER_GROUP_STAT_lOG = "consumer-topic-mqtt-stat-log-spark";

    @Autowired
    private IKafkaMesgService mesgService;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTopicTest.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(SAMPLE_DATA_FILE)))){

            String line = null;
            while((line = reader.readLine()) != null) {
                mesgService.sendMessage(TOPIC_STAT_LOG, line);

                Thread.sleep(200);
            }

        }
    }

    @KafkaListener(topics = TOPIC_STAT_LOG, groupId = CONSUMER_GROUP_STAT_lOG)
    public void handleLogMesg(String mesg) {
        LOGGER.info("In handleLogMesg: {}", mesg);
    }

}
