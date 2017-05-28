package pawelkat.fyber

import pawelkat.fyber.TimeSeries
import org.scalatest._

import collection.mutable.Stack

import scala.collection.mutable.ListBuffer

class TimeSeriesTest extends FlatSpec with Matchers{
  
  "TimeSeries" should "print output with no exception " in {

  	println("start")

    TimeSeries.processInput(getClass.getResource("/data_scala.txt").getPath, 60, TimeSeries.printHeader, TimeSeries.printOutput)

  }
  //checking the algorithm
  it should "return valid output for some fake data" in {
  		//defining the data structures
	case class Output(t:Int, r:Double, n:Int, rs:Double, minV:Double, maxV:Double)

  	var timeWindowBuffer = ListBuffer[TimeSeries.Line]()

  	val testLines = List(
  		TimeSeries.Line(0, 0.0),
  		TimeSeries.Line(10, 1.0),
  		TimeSeries.Line(30, 10.0),
  		TimeSeries.Line(70, 1.0),
  		TimeSeries.Line(120, 1.0),
  		TimeSeries.Line(160, 10.0)
  	)
  	val outputs = testLines.map( l=>{
	  		timeWindowBuffer = timeWindowBuffer.filter(elem => l.timestamp - elem.timestamp <= 60) += l
	        val timeWindowRatioList = timeWindowBuffer.map(_.ratio)
	        Output(l.timestamp, l.ratio, timeWindowBuffer.size, timeWindowRatioList.sum, timeWindowRatioList.min, timeWindowRatioList.max)
    	}
  	)

  	//some help to copy/paste
  	outputs.view.zipWithIndex.foreach(o => println(s"outputs(${o._2}) should equal (${o._1})"))
  	//outputs.foreach(println)

  	outputs(0) should equal (Output(0,0.0,1,0.0,0.0,0.0))
	outputs(1) should equal (Output(10,1.0,2,1.0,0.0,1.0))
	outputs(2) should equal (Output(30,10.0,3,11.0,0.0,10.0))
	outputs(3) should equal (Output(70,1.0,3,12.0,1.0,10.0))
	outputs(4) should equal (Output(120,1.0,2,2.0,1.0,1.0))
	outputs(5) should equal (Output(160,10.0,2,11.0,1.0,10.0))

  }
}