package ireulToolsForScala.datetime

import org.scalatest.{FlatSpec, Matchers}


/**
  * Created by tech0039 on 2017/4/12.
  */
class TestImmutableDatetime extends FlatSpec with Matchers {


    it should "calculate date correctly " in {

        val dt1 = ImmutableDatetime(2016, 5, 4, 23, 30, 59, 0)
        val dt2 = dt1 + Day(90)
        println(dt2.toString("yyyy-MM-dd HH:mm:ss"))
        dt2.toString("yyyy-MM-dd HH:mm:ss") should be("2016-08-02 23:30:59")

        val dt3 = ImmutableDatetime(2016, 5, 4, 23, 30, 59, 0)
        val dt4 = dt3 + Millis(48 * 60 * 60 * 1000)
        println(dt4.toString("yyyy-MM-dd HH:mm:ss"))
        dt4.toString("yyyy-MM-dd HH:mm:ss") should be("2016-05-06 23:30:59")

        // 閏年
        val dt5 = ImmutableDatetime(2016, 2, 27, 23, 30, 59, 0)
        val dt6 = dt5 + Day(2)
        println(dt6.toString("yyyy-MM-dd HH:mm:ss"))
        dt6.toString("yyyy-MM-dd HH:mm:ss") should be("2016-02-29 23:30:59")

        // 沒閏年
        val dt7 = ImmutableDatetime(2015, 2, 27, 23, 30, 59, 0)
        val dt8 = dt7 + Day(2)
        println(dt8.toString("yyyy-MM-dd HH:mm:ss"))
        dt8.toString("yyyy-MM-dd HH:mm:ss") should be("2015-03-01 23:30:59")

        // 日期減日期
        val dt9 = ImmutableDatetime(2015, 2, 27, 23, 30, 6, 0)
        val dt10 = ImmutableDatetime(2015, 2, 25, 12, 29, 7, 0)
        val diff1 = dt9 - dt10
        val diff2 = dt10 - dt9
        "2 days, 11 hours, 0 minutes, 59 seconds and 0 milliseconds" should be(diff1.toString)
        println(diff1.totalDay)
        Math.abs(diff1.totalDay - 2.4590162037037033) < 0.00001 should be(true)
        Math.abs(diff1.totalMin + diff2.totalMin) < 0.00001 should be(true)
        dt9 > dt10 should be(true)
        dt10 < dt9 should be(true)
        dt10 >= dt10 should be(true)
        dt10 <= dt10 should be(true)

    }


    it should "generate zero day and compare correctly " in {
        val dt1 = ImmutableDatetime.zeroDay
        println(dt1.timeZone.toString())
        println(dt1.toString())

        val dt2 = ImmutableDatetime.readFrom("1970-01-01 08:00:00.000", "yyyy-MM-dd HH:mm:ss.SSS")
        println(dt2.timeZone.toString())
        println(dt2.toString())
        println(dt2.toUTC.toString())

        dt1 == dt2 should be(true)
    }


    it should "be correct time zone and immutable" in {
        val dt1 = ImmutableDatetime.now
        println(s"${dt1.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dt1.timeZone}")

        val dtutc = dt1.toUTC
        println(s"${dt1.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dt1.timeZone}")
        println(s"${dtutc.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dtutc.timeZone}")

        val dtlocal = dt1.toLocalTime
        println(s"${dtlocal.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dtlocal.timeZone}")

        val dt3 = ImmutableDatetime.readFrom("2016-10-25 16:07:21 TST", "yyyy-MM-dd HH:mm:ss zzz")
        println(s"${dt3.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dt3.timeZone}")

        //!!!!!!!!!! it will get CEST. I don't know why ????????
        val dt4 = ImmutableDatetime.readFrom("2016-10-25 16:07:21 UTC", "yyyy-MM-dd HH:mm:ss zzz")
        println(s"${dt4.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dt4.timeZone}")

        val dt5 = ImmutableDatetime.readFrom("2016-10-25 16:07:21", "yyyy-MM-dd HH:mm:ss")
        println(s"${dt5.toString("yyyy-MM-dd HH:mm:ss zzz ")}  ${dt5.timeZone}")
    }


    it should "constract correctly" in {

        val dt3 = ImmutableDatetime(2015, 8, 4, 9, 10, 15, 0)
        "2015-08-04 09:10:15.000" should be(dt3.toString())
        "2015-08-04 00:00:00.000" should be(dt3.toBeginOfDay.toString())
        "2015-08-04 23:59:59.999" should be(dt3.toEndOfDay.toString())
        1438650615000l should be(dt3.stamp)

        val dt4 = ImmutableDatetime(dt3.stamp)
        dt3 == dt4 should be(true)

        val dt5 = ImmutableDatetime.readFrom("20140825130972321", "yyyyMMddHHmmssSSS")
        "2014-08-25 13:10:12.321" should be(dt5.toString())

        val dt6 = ImmutableDatetime.readFrom("2010-03-01 10:00:00", "yyyy-MM-dd HH:mm:ss")
        val dt6Calc = dt6 - Day(3) + Hour(73) - Min(120) + Sec(5) + Millis(1500)
        "2010-03-01 09:00:06.500" should be(dt6Calc.toString())
        dt6 == dt6Calc should be(false)


    }



}
