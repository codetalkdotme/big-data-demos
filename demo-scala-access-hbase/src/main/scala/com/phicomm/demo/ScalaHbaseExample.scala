package com.phicomm.demo

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes

object ScalaHbaseExample extends App{


  def printRow(result : Result) = {
    val cells = result.rawCells();
    print( Bytes.toString(result.getRow) + " : " )
    for(cell <- cells){
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      print("(%s,%s) ".format(col_name, col_value))
    }
    println()
  }


  val conf: Configuration = HBaseConfiguration.create()
//  val ZOOKEEPER_QUORUM =
  conf.set("hbase.zookeeper.quorum", "172.31.34.128:2181")
//  conf.set("hbase.zookeeper.quorum", "172.31.34.128")
//  conf.set("hbase.zookeeper.property.clientPort", "2181")

  val connection = ConnectionFactory.createConnection(conf)
  val table = connection.getTable(TableName.valueOf( Bytes.toBytes("stat_mqtt_device_events") ) )

  // Put example
  var put = new Put(Bytes.toBytes("00D0BAE4500BC99991B578E0_1522406994000"))
  put.addColumn(Bytes.toBytes("event"), Bytes.toBytes("n"), Bytes.toBytes("SUB"))
  put.addColumn(Bytes.toBytes("event"), Bytes.toBytes("b"), Bytes.toBytes("172.31.34.128"))
  put.addColumn(Bytes.toBytes("event"), Bytes.toBytes("t"), Bytes.toBytes("$events/broker/00D0BAE4500BC99991B578E0/binded/1524569785123 "))
  table.put(put)

  // Get example
  println("Get Example:")
  var get = new Get(Bytes.toBytes("00D0BAE4500BC99991B578E0_1522406994000"))
  var result = table.get(get)
  printRow(result)

  table.close()
  connection.close()
}
