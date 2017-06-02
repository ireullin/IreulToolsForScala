package ireulToolsForScala.math

object GaussianDistribution{
    def apply(mean: Double, stdev: Double, area: Double, value:Double):Double = {
        new GaussianDistribution(mean,stdev,area).weight(value)
    }

    def apply(mean: Double, stdev: Double, value:Double):Double = {
        new GaussianDistribution(mean,stdev,1).weight(value)
    }
}


class GaussianDistribution(mean:Double,stdev:Double,area:Double) {

    def this(mean:Double,stdev:Double) = this(mean, stdev, 1)

    private val constant1 = area / (stdev * Math.sqrt(2 * Math.PI))
    private val constant2 = 2 * Math.pow(stdev,2)

    def weight(value:Double):Double ={
        val tmp = Math.pow(value-mean, 2) / constant2
        constant1 * Math.exp(-tmp)
    }

}
