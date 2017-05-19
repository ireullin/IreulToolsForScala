package ireulToolsForScala.sql.statements

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by tech0039 on 2017/5/19.
  */
class TestInsert extends FlatSpec with Matchers {

    it should "generate sql syntax correctly" in {

        val insertStr1 = Insert.into("table1")
                .put("field1", "1")
                .put("field2", 2)
                .toString

        println(insertStr1)
        insertStr1 should be("insert into table1 (field1,field2) values ('1','2')")


        val insertStr2 = Insert.into("recommendations_20161108_03")
                .put("member_id", "ireullin")
                .put("recommendation_list", "[]")
                .put("created_at", "2016-08-04 00:00:00")
                .returning("member_id")
                .toString

        println(insertStr2)
        insertStr2 should be("insert into recommendations_20161108_03 (member_id,recommendation_list,created_at) values ('ireullin','[]','2016-08-04 00:00:00') returning member_id")
    }

}
