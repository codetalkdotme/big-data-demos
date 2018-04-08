package com.phicomm.demo.scala

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(Array(
  "com.phicomm.demo.scala.runner"
))
class DemoMain

object DemoMain {

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[DemoMain])
  }

}
