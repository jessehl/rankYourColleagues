package ranking 

import scala.io.Source


private object Csv {


// Returns tab-separated values as Seq of Maps.
def parse(filename: String): Seq[Map[String, String]] = {
    val lines = Source.fromFile(filename, "utf-8").getLines.toList
    val header = lines.head.split("\t")
    lines.tail.map(line => header.zip(line.split("\t")).toMap)
  }


}