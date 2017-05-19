package ireulToolsForScala.sql.connections

import java.sql.{DriverManager, PreparedStatement}

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

object PostgreSqlConnection{
    def apply(params: Map[String, String]): IConnection = new PostgreSqlConnection(params)

    def apply(host:String,dbname:String,user:String,password:String): IConnection = {
        new PostgreSqlConnection(Map("host" -> host, "dbname" -> dbname, "user" -> user, "password" -> password))
    }
}


private class PostgreSqlConnection(params:Map[String,String]) extends IConnection{

    private val cn = {
        val url = s"jdbc:postgresql://${params("host")}:${params.getOrElse("port","5432")}/${params("dbname")}"
        val user = params("user").toString
        val password = params("password").toString
        DriverManager.getConnection(url,user,password)
    }

    def query[T](cmd:String)(callback:IRow => T):Seq[T] = {
        val ps = this.cn.prepareStatement(cmd)
        val row = new Row(ps.executeQuery())
        val buff = new ArrayBuffer[T]()

        try{
            breakable {
                while (row.next) {
                    buff += callback(row)
                }
            }
        }
        catch{
            case e:Exception => throw e
        }
        finally {
            row.close
            ps.close()
        }
        buff
    }

    def execMutiCmd(cmd:String):IConnection = {

        val ps = this.cn.prepareStatement(cmd)
        try {
            val rc = ps.executeUpdate
        }
        catch{
            case e:Exception => throw e
        }
        finally {
            ps.close()
        }

        this
    }


    override def close(): Unit = this.cn.close()
}
