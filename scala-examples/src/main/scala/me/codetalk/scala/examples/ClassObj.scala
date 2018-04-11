package me.codetalk.scala.examples

// Ref: https://docs.scala-lang.org/tour/classes.html

class ClassObj

object ClassObj {

  def main(args: Array[String]): Unit = {
    val user = new User
    println(user) // me.codetalk.scala.examples.User@16c0663d

    val p1 = new Point("p1", 1, 2)  // z defaults as 0
    println(p1)   // [p1](1, 2, 0)
    p1.move(3, -2, 5)
    println(p1)   // [p1](4, 0, 5)


    val p2 = new Point("p2", 3, -6, 9)  // z as 9
    println(p2)   // [p2](3, -6, 9)

    println(p2.y) // -6

    println(p2.id)  // p2

    p2.id = "Point-2"

    println(p2.id)  // Point-2
  }

}

// Empty Class
class User

// Custom Constructor
//
// x | y | z 是成员变量, 如果使用var | val修饰时, 这些成员变量是public的,
// 如果没有var | val修饰, 则默认是val 并且是private的
// 如果需要private var成员变量, 则需要显示声明如: `private var _id`
//
// 支持成员变量默认值
class Point(private var _id: String, var x: Int, var y: Int, var z: Int = 0) {

  def move(dx: Int, dy: Int, dz: Int): Unit = {
    x = x + dx
    y = y + dy
    z = z + dz
  }

  // Getter & Setter
  def id = _id

  def id_=(newId: String): Unit = {
    _id = newId
  }

  override def toString: String = // override
    s"[${_id}]($x, $y, $z)"

}

