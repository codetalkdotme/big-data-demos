package me.codetalk.demo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by guobiao.xu on 2018/7/2.
 */
@Component
public class LogGenRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogGenRunner.class);

    private static final int MIN_WORD_LEN = 10;
    private static final int MAX_WORD_LEN = 32;

    private static final int MIN_LOG_WORDS = 20;
    private static final int MAX_LOG_WORDS = 50;

    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOOPQRSTUVWXYZ0123456789";
    private static final int CHAR_LEN = CHARS.length();

    private static final Random rand = new Random();


    @Override
    public void run(String... strings) throws Exception {
        while(true) {
            Thread.sleep(1);

            String randLog = randomLog();
            LOGGER.info(randLog);
        }
    }

    protected String randomLog() {
        int len = rand.nextInt(MAX_LOG_WORDS - MIN_LOG_WORDS + 1) + MIN_LOG_WORDS;

        StringBuffer buffer = new StringBuffer(512);

        for(int i =0; i< len; i++) {
            buffer.append(randomWord()).append(" ");
        }

        return buffer.toString();
    }

    private String randomWord() {
        int len = rand.nextInt(MAX_WORD_LEN - MIN_WORD_LEN + 1) + MIN_WORD_LEN;

        char[] chars = new char[len];
        for(int i = 0; i < len; i++) {
            chars[i] = randChar();
        }

        return new String(chars);
    }

    private char randChar() {
        return CHARS.charAt(rand.nextInt(CHAR_LEN));
    }

}
