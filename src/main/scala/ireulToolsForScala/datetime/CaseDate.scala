package ireulToolsForScala.datetime

/**
  * Created by tech0039 on 2017/4/14.
  */
trait CaseDate
case class Day(v:Int) extends CaseDate
case class Hour(v:Int) extends CaseDate
case class Min(v:Int) extends CaseDate
case class Sec(v:Int) extends CaseDate
case class Millis(v:Int) extends CaseDate
