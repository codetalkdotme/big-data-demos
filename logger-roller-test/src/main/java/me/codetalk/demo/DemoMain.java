package me.codetalk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by guobiao.xu on 2018/7/2.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.demo.runner"
})
public class DemoMain {

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class);
    }

}
