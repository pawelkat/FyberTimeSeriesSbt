package pawelkat.fyber

import scala.collection.mutable.ListBuffer
import scala.io.{Source, BufferedSource}

import scala.util.Try

//val filename = "/home/pawel/workspace/FyberTimeSeries/data/data_scala.txt"



object TimeSeries {
	//defining the data structures
	case class Line(timestamp:Int, ratio:Double)
	case class Output(t:Int, r:Double, n:Int, rs:Double, minV:Double, maxV:Double)


	def processInput(filename:String, timeWindow:Int, headerRenderer: () => Unit, outputRenderer: Output => Unit) ={
	    
	    //Intertionally mutable buffer to hold Lines in the specified time window
	    var timeWindowBuffer = ListBuffer[Line]()

	    //trying to open the file
    	val sourceFile: Option[Source] =     	
    		try{
    			Some(Source.fromFile(filename))
	        }catch{
	        	case e:java.io.FileNotFoundException => println("File not found")//e.printStackTrace
	        	System.exit(1)
	        	None
	        }
	    

	    //printing passed header renderer
	    headerRenderer()
	    
        sourceFile.get.getLines()
        //splitting individual lines and creating the input Line object
        .map(line => {
            val splited = line.split("\\s+")
            Line(splited(0).trim.toInt, splited(1).trim.toDouble)
        })
        //manipulating the timeBuffer list for each pass over the input line
        .map(l => {
            //filtering the elements of timeBuffer so that they are no "older" than 60s; adding the current line to the buffer
            timeWindowBuffer = timeWindowBuffer.filter(elem => l.timestamp - elem.timestamp <= timeWindow) += l
            val timeWindowRatioList = timeWindowBuffer.map(_.ratio)
            Output(l.timestamp, l.ratio, timeWindowBuffer.size, timeWindowRatioList.sum, timeWindowRatioList.min, timeWindowRatioList.max)
        })
        //calling passed function to render the individual output row
        .foreach(outputRenderer(_))
	}


	// the helper function for the printing renderer
	val formatDouble = {d:Double => "%.5f".format(d)}

	//implementation of the rendering functions
	val printHeader = {() => println(s"""T\t\tV\tN\tRS\tMinV\tMaxV""")}
	val printOutput = {o:Output => 
	    println(o.t + "\t" + formatDouble(o.r) + "\t" + o.n + "\t" + formatDouble(o.rs) + "\t" + formatDouble(o.minV) + "\t" + formatDouble(o.maxV))
	} 
	
	def main(args: Array[String]) = {
		  // time Window
		val timeWindow = 60

		//processInput(filename, timeWindow, printHeader, printOutput)
		args.length match {
			case 0 => println("Enter input file path")
			case 1 => processInput(args(0), timeWindow, printHeader, printOutput)
		} 
	}
}