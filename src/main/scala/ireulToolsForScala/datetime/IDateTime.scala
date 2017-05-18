package ireulToolsForScala.datetime

import java.util.TimeZone

/**
  * Created by tech0039 on 2017/4/12.
  */
trait IDateTime {
    def year: Int

    def month: Int

    def day: Int

    def hour: Int

    def min: Int

    def sec: Int

    def millis: Int

    def stamp: Long

    def timeZone: TimeZone

    def toString(format: String): String

    def toBeginOfDay: IDateTime

    def toEndOfDay: IDateTime

    def toTimeZone(tz: TimeZone): IDateTime

    def toUTC: IDateTime

    def toLocalTime: IDateTime

    def + (v:CaseDate):IDateTime

    def - (v:CaseDate):IDateTime

    def - (v:IDateTime):TimeInterval

    def == (dt: IDateTime):Boolean

    def >= (dt: IDateTime):Boolean

    def <= (dt: IDateTime):Boolean

    def > (dt: IDateTime):Boolean

    def < (dt: IDateTime):Boolean
}
