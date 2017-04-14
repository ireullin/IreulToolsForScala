package ireulToolsForScala.json

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Wrap com.fasterxml.jackson for scala using
  *
  * Example:
      // 取得元素
      val test1 = s"""{"a":"a1", "b":[1,2,3,4]  }"""
      val obj = Json.parse(test1)
      println(obj("a").toString)
      println(obj("b").toString)
      obj("b").foreach {
        case Json.Array(i, n) => {println(s"index:$i value:$n")}
      }

      // 走訪整個結構
      def recurrenceNode(it:Json.Iterator):Unit ={
        it match {
          case Json.Array(i, n) => {
            println(s"index:$i value:$n")
            n.foreach(recurrenceNode)
          }
          case Json.Map(k, n) => {
            println(s"key:$k value:$n")
            n.foreach(recurrenceNode)
          }
          case Json.Value(v) => {
            println(v)
          }
        }
      }

      obj.foreach(recurrenceNode)
  *
  */
object Json {

    private val om = new ObjectMapper() with ScalaObjectMapper
//    private val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)

    abstract class Iterator
    case class Array(i:Int, n:Node) extends Iterator
    case class Map(k:String, n:Node) extends Iterator
    case class Value(n:Node) extends Iterator


    class Node(jsonNode: JsonNode){

        private val node = jsonNode

        def apply(index: Int): Node = {
            new Node(node.get(index))
        }

        def apply(name: String): Node = {
            new Node(node.get(name))
        }

        override def toString: String = {
            if(node.isArray || node.isObject) {
                Json.stringify(node)
            }
            else {
                node.asText()
            }
        }

        def toString(default:String): String = node.asText(default)

        def toInt: Int = node.asInt()
        def toInt(default:Int): Int = node.asInt(default)

        def toLong: Long = node.asLong()
        def toLong(default:Long): Long = node.asLong(default)

        def toBoolean: Boolean = node.asBoolean()
        def toBoolean(default:Boolean): Boolean = node.asBoolean(default)

        def toDouble: Double = node.asDouble()
        def toDouble(default:Double): Double = node.asDouble(default)

        def foreach(f: (Json.Iterator) => Unit): Unit ={
            if(node.isArray) {
                var i=0
                node.asScala.foreach(n => {
                    f(Json.Array(i, new Node(n)))
                    i += 1
                })

            }
            else if(node.isObject){
                node.fields().asScala.foreach( x =>
                    f(Json.Map(x.getKey, new Node(x.getValue)))
                )
            }
            else{
                // throw new Exception("This object is not array or map")
                f(Json.Value(this))
            }
        }

        def map[U](f: (Json.Iterator) => U): Traversable[U] ={
            val buff = new mutable.ArrayBuffer[U]()
            if(node.isArray) {
                var i=0
                node.asScala.foreach(n => {
                    buff += f(Json.Array(i, new Node(n)))
                    i += 1
                })
            }
            else if(node.isObject){
                node.fields().asScala.foreach( x =>
                    buff += f(Json.Map(x.getKey, new Node(x.getValue)))
                )
            }
            else{
                // throw new Exception("This object is not array or map")
                buff += f(Value(this))
            }
            buff
        }

        def isValue:Boolean = { !node.isArray && !node.isObject }

        def isArray:Boolean =  node.isArray

        def isMap:Boolean =  node.isObject

        def size:Int = node.size()

        def has(fieldName:String):Boolean = node.has(fieldName)
    }

    def stringify(data:Any):String = om.writeValueAsString(data)

    def parse(data:String):Node = {
        val node = om.readTree(data)
        new Node(node)
    }
}