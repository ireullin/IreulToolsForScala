package ireulToolsForScala.datetime

/**
  * Created by tech0039 on 2017/5/18.
  */
class TimeInterval(stamp:Long) {
    case class Partial(days:Int,hours:Int,minutes:Int,seconds:Int,milliseconds:Int)

    lazy val partial:Partial = {
        val absStamp = Math.abs(stamp)
        val days = (absStamp / (24 * 60 * 60 * 1000)).toInt
        val dayRem = (absStamp % (24 * 60 * 60 * 1000)).toInt
        val hours = dayRem / (60 * 60 * 1000)
        val hoursRem = dayRem % (60 * 60 * 1000)
        val minutes = hoursRem / (60 * 1000)
        val minRem = hoursRem % (60 * 1000)
        val seconds = minRem / 1000
        val milliseconds = minRem % 1000
        Partial(days,hours,minutes,seconds,milliseconds)
    }

    def totalDay: Double = totalHour / 24d

    def totalHour: Double = totalMin / 60d

    def totalMin: Double = totalSec / 60d

    def totalSec: Double = totalMillis / 1000d

    def totalMillis: Long = this.stamp

    override def toString: String = s"${partial.days} days, ${partial.hours} hours, ${partial.minutes} minutes, ${partial.seconds} seconds and ${partial.milliseconds} milliseconds"
}
