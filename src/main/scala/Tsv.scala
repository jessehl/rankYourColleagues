package ranking 

import scala.io.Source


private object Tsv {


/* Returns tab-separated values file as a Seq of Maps.

The assumption is that the values in the file don't contain tabs or newlines. */
def parse(filename: String): Seq[Map[String, String]] = {
    val lines = Source.fromFile(filename, "utf-8").getLines.toList
    val header = lines.head.split("\t")
    lines.tail.map(line => header.zip(line.split("\t")).toMap)
  }


}