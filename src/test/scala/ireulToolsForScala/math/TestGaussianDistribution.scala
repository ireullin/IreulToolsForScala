package ireulToolsForScala.math

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by tech0039 on 2017/6/2.
  */
class TestGaussianDistribution extends FlatSpec with Matchers {

    it should "calculate weight correctly " in {
        val stdev = 3.0
        val mean = 15
        val area = 100

        val gd = new GaussianDistribution(mean, stdev, area)
        var sum = 0.0
        0.to(30).foreach{ i =>
            val weight = gd.weight(i)
            sum += weight
            println(s"$i $weight")
        }
        area - sum < 0.01 should be(true)
    }
}
