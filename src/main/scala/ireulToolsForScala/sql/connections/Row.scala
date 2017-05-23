package ireulToolsForScala.sql.connections

import java.sql.ResultSet

import scala.collection.mutable

/**
  * Created by tech0039 on 2017/5/18.
  */
class Row(rs:ResultSet) extends IRow{

    private var _index = -1

    def index:Int = _index

    def next:Boolean = {
        val rc = rs.next()
        _index += 1
        rc
    }

    def close:Unit = rs.close()

    override def apply(columnIndex:Int):String = rs.getString(columnIndex)

    override def apply(columnName:String):String = rs.getString(columnName)

    override lazy val size:Int = {
        try { rs.getMetaData.getColumnCount }
        catch { case e: Exception => -1 }
    }

    override def toMap:Map[String,String] = {
        val buff = new mutable.HashMap[String,String]()
        1.to(this.size).foreach { i =>
            val k = rs.getMetaData.getColumnName(i)
            val v = apply(i)
            buff.put(k,v)
        }
        buff.toMap
    }

    override def toSeq:Seq[String] = {
        val buff = new mutable.ArrayBuffer[String]()
        1.to(this.size).foreach(i => buff += this.apply(i) )
        buff
    }
}
