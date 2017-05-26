package pawelkat.fyber

import pawelkat.fyber.TimeSeries
import org.scalatest._

import collection.mutable.Stack


class TimeSeriesTest extends FlatSpec {
  
  "TimeSeries" should "print output" in {

  	println("start")

    TimeSeries.processInput(getClass.getResource("/data_scala.txt").getPath, 60, TimeSeries.printHeader, TimeSeries.printOutput)

  }
}