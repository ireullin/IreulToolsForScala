package ireulToolsForScala.datetime

import java.text.{SimpleDateFormat}
import java.util.{Calendar, Date, GregorianCalendar, TimeZone}


object ImmutableDatetime{
    def apply(): IDateTime = new ImmutableDatetime()

    def apply(year: Int, month: Int, day: Int): IDateTime = apply(year,month,day,0,0,0,0)

    def apply(year: Int, month: Int, day: Int, tz: TimeZone): IDateTime = apply(year,month,day,0,0,0,0,tz)

    def apply(year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int, millis: Int, tz: TimeZone): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.set(year, month - 1, day, hour, min, sec)
        d.calendar.set(Calendar.MILLISECOND, millis)
        d.calendar.setTimeZone(tz)
        d
    }

    def apply(year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int, millis: Int): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.set(year, month - 1, day, hour, min, sec)
        d.calendar.set(Calendar.MILLISECOND, millis)
        d
    }

    def apply(stamp: Long): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.setTimeInMillis(stamp)
        d
    }

    def apply(stamp: Long, tz: TimeZone): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.setTimeInMillis(stamp)
        d.calendar.setTimeZone(tz)
        d
    }

    def apply(tz: TimeZone): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.setTimeZone(tz)
        d
    }

    def apply(dt: Date): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.setTime(dt)
        d
    }

    def apply(dt: Date, tz: TimeZone): IDateTime = {
        val d = new ImmutableDatetime()
        d.calendar.setTime(dt)
        d.calendar.setTimeZone(tz)
        d
    }

    def apply(dt: IDateTime): IDateTime = {
        new ImmutableDatetime(dt)
    }

    def now:IDateTime = new ImmutableDatetime()

    def zeroDay:IDateTime = apply(0)

    def readFrom(dtstr: String, format: String): IDateTime = {
        val sdf = new SimpleDateFormat(format)
        val date = sdf.parse(dtstr)
        val tz = sdf.getTimeZone
        val dt = apply(date, tz)
        dt
    }
}


/**
  * Created by tech0039 on 2017/4/12.
  */
private class ImmutableDatetime extends IDateTime{

    private val calendar = new GregorianCalendar

    private def this(dt: IDateTime) = {
        this()
        calendar.setTimeInMillis(dt.stamp)
        calendar.setTimeZone(dt.timeZone)
    }

    override def year: Int = calendar.get(Calendar.YEAR)

    override def month: Int = calendar.get(Calendar.MONTH) + 1

    override def day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override def hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

    override def min: Int = calendar.get(Calendar.MINUTE)

    override def sec: Int = calendar.get(Calendar.SECOND)

    override def millis: Int = calendar.get(Calendar.MILLISECOND)

    override def stamp: Long = calendar.getTimeInMillis()

    override def timeZone: TimeZone = calendar.getTimeZone()

    override def toBeginOfDay: IDateTime = ImmutableDatetime(year, month, day, 0,0,0,0, timeZone)

    override def toEndOfDay: IDateTime = ImmutableDatetime(year, month, day, 23,59,59,999, timeZone)

    override def toTimeZone(tz: TimeZone): IDateTime = ImmutableDatetime(stamp, tz)

    override def toUTC: IDateTime = ImmutableDatetime(stamp, TimeZone.getTimeZone("UTC"))

    override def toLocalTime: IDateTime = ImmutableDatetime(stamp, TimeZone.getDefault())

    override def clone(): AnyRef = ImmutableDatetime(stamp, timeZone)

    override def toString(format: String): String = {
        val sdf = new SimpleDateFormat(format)
        sdf.setTimeZone(timeZone)
        sdf.format(calendar.getTime)
    }

    override def toString: String = toString("yyyy-MM-dd HH:mm:ss.SSS")

    override def ==(dt: IDateTime):Boolean = this.stamp == dt.stamp

    override def + (v:CaseDate):IDateTime = {
        val dt = new ImmutableDatetime(this)
        var rc = v match {
            case Day(v:Int) => { dt.calendar.add(Calendar.DATE, v); dt }
            case Hour(v:Int) => { dt.calendar.add(Calendar.HOUR, v); dt }
            case Min(v:Int) => { dt.calendar.add(Calendar.MINUTE, v); dt }
            case Sec(v:Int) => { dt.calendar.add(Calendar.SECOND, v); dt }
            case Millis(v:Int) => { dt.calendar.add(Calendar.MILLISECOND, v); dt }
        }
        rc
    }

    override def - (v:CaseDate):IDateTime = {
        val dt = new ImmutableDatetime(this)
        var rc = v match {
            case Day(v:Int) => { dt.calendar.add(Calendar.DATE, -v); dt }
            case Hour(v:Int) => { dt.calendar.add(Calendar.HOUR, -v); dt }
            case Min(v:Int) => { dt.calendar.add(Calendar.MINUTE, -v); dt }
            case Sec(v:Int) => { dt.calendar.add(Calendar.SECOND, -v); dt }
            case Millis(v:Int) => { dt.calendar.add(Calendar.MILLISECOND, -v); dt }
        }
        rc
    }

}
