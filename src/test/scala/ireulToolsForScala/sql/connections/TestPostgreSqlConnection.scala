package ireulToolsForScala.sql.connections

import org.scalatest.{FlatSpec, Matchers}
import util.control.Breaks._

/**
  * Created by tech0039 on 2017/5/19.
  */
class TestPostgreSqlConnection extends FlatSpec with Matchers {

    it should "connect correctly" in {
        val params = Map(
            "host" -> "10.3.197.66",
            "dbname" -> "ProductionLog",
            "user" -> "postgres",
            "password" -> "postgres"
        )

        val cmd = "SELECT * FROM members limit 100"
        val cn = PostgreSqlConnection(params)
        val queried = cn.query(cmd){ r =>
            val forShow = s"${r.size} ${r.index} ${r("ga")}"
            if(r.index==10) {
                break
            }
            else
                forShow

        }.map(_.substring(0,6))

        queried.foreach(x => println(x))

        cn.close()
    }
}
