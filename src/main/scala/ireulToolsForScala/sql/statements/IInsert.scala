package ireulToolsForScala.sql.statements

/**
  * Created by tech0039 on 2017/5/19.
  */
trait IInsert {
    def put(column:Any, value:Any):IInsert
    def returning(column: Any): IInsert
}
