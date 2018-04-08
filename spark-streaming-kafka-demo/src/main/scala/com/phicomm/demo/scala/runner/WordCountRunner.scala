package com.phicomm.demo.scala.runner

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class WordCountRunner extends CommandLineRunner {

  override def run(strings: String*): Unit = {
    val conf = new SparkConf()
      .setAppName("SparkStreamingKafka010Demo")
      .setMaster("local[*]")
      .set("spark.streaming.kafka.maxRatePerPartition", "100")

    val sc = new SparkContext(conf)

    val streamingContext = new StreamingContext(sc, Seconds(10))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.31.34.237:9090",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "consumer-group-topic-words-testx-1",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val topics = Array("topic-words-testx")

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    stream.map(record => record.value)
      .flatMap(line => line.split(" "))
      .map(word => { println(word); word.toLowerCase })
      .filter(_.size > 0)
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .repartition(1)
      .transform(rdd => rdd.sortBy(-_._2))
      .print()

    //    val words = lines.flatMap(_.split(" "))
    //    val pairs = words.map(word => (word, 1))
    //    val wordCounts = pairs.reduceByKey(_ + _)
    //    wordCounts.print()

    streamingContext.start()
    streamingContext.awaitTerminationOrTimeout(1000 * 30)

//    var numberInst = 0
//    while (numberInst < 10){
//      streamingContext.awaitTerminationOrTimeout(1000)
//
//      numberInst += 1
//    }
//
//    streamingContext.stop()
  }

}
