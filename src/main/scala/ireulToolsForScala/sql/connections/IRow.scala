package ireulToolsForScala.sql.connections



/**
  * Created by tech0039 on 2017/5/18.
  */
trait IRow {
    def apply(columnIndex:Int):String
    def apply(columnName:String):String
    def size:Int
    def index:Int
}
