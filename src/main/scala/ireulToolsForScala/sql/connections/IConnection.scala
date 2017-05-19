package ireulToolsForScala.sql.connections

/**
  * Created by tech0039 on 2017/5/18.
  */
trait IConnection extends AutoCloseable{
    def query[T](cmd:String)(callback:IRow => T):Seq[T]
    def execMutiCmd(cmd:String):IConnection
}
