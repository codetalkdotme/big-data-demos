package com.example

import java.lang.reflect.Array

import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(Array(
  "com.example.runner",
  "com.example.service"
))
class HelloApp

object HelloApp {

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[HelloApp])
  }

}
