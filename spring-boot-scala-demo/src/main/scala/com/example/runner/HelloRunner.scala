package com.example.runner

import com.example.service.HelloService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class HelloRunner(@Autowired val helloService: HelloService) extends CommandLineRunner {

  @Value("${titlemsg}")
  var titleMsg: String = null;

  var logger = LoggerFactory.getLogger(classOf[HelloRunner])

  override def run(strings: String*): Unit = {
//    println("Hello from Command line runner!")
//    println(helloService.hello())

    logger.info("In run...")

    println(titleMsg)
  }

}

