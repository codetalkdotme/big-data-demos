package com.example.service

import org.springframework.stereotype.Service

@Service
class HelloService {

  def hello(): String = {
    return "Hello from HelloService!"
  }

}
