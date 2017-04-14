package ireulToolsForScala.json

import org.scalatest.{FlatSpec, Matchers}


/**
  * Created by tech0039 on 2017/4/12.
  */
class TestJson extends FlatSpec with Matchers {

    it should "extract json nodes correctly" in {

        val src = s"""{"a":"a1", "b":[1,2,3,4]  }"""
        val obj = Json.parse(src)
        obj("a").toString should be("a1")
        obj("b").toString should be("[1,2,3,4]")
        obj("b").foreach {
            case Json.Array(0, n) => n.toString should be("1")
            case Json.Array(1, n) => n.toString should be("2")
            case Json.Array(2, n) => n.toString should be("3")
            case Json.Array(3, n) => n.toString should be("4")
        }
    }

    it should "stringfy a collection to a json string" in {

        val src = Seq(
            Map("name"->"Ireul", "age"->35),
            Map("name"->"Jack", "age"->20),
            Map("name"->"Sucker", "age"->17)
        )

        val result = Json.stringify(src)
        result should be("[{\"name\":\"Ireul\",\"age\":35},{\"name\":\"Jack\",\"age\":20},{\"name\":\"Sucker\",\"age\":17}]")
    }

}
