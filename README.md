# FyberTimeSeries

## Instalation and running
`sbt`

`run /path-to-file/data_scala.txt`

## Tests (Scalatest)
`sbt test`

## Discussion
### Performance and memory considerations. 
First solution that's comming to mind is the use of streaming. Though the class collection.immutable.Stream that is available in the standard library is not suitable. Referring the documentation http://docs.scala-lang.org/tutorials/FAQ/stream-view-iterator streams makes memoization.

Using simple iterator seems to be an optimal solution in this case. 

