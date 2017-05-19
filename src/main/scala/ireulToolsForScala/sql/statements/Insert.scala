package ireulToolsForScala.sql.statements

import scala.collection.mutable.ArrayBuffer


object Insert{
    def into(tableName:String):IInsert = new Insert(tableName)
}


/**
  * Created by tech0039 on 2017/5/19.
  */
private class Insert(tableName:String) extends IInsert{

    private val columns = new ArrayBuffer[Any]()
    private val values = new ArrayBuffer[Any]()
    private var returning:String = ""

    override def put(column: Any, value: Any) = {
        columns += column
        values += value
        this
    }

    override def returning(v: Any) = {
        returning = v.toString
        this
    }

    override def toString: String = {

        val cols = columns.map(_.toString).mkString(",")
        val vals = values.map(_.toString).mkString("','")
        val cmd = s"insert into $tableName ($cols) values ('$vals')"
        if(returning.isEmpty)
            cmd
        else
            cmd + " returning " + returning

    }
}
