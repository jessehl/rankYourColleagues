package ranking 

import java.time.format.DateTimeFormatter


case class Answer(
  val contestant: String, 
  val timeSent: String,
  val question: String,
  val names: Seq[String]
  )
  {

  override def toString = s"(question: $question | contestant: $contestant | names: ${names.mkString(", ")})"
}


object Answers {

  val contestantColumn = "E-mailadres"
  val timeSentColumn = "Tijdstempel"
  val header = List(contestantColumn, timeSentColumn)

  def fromRecord(record: Map[String, String]): Seq[Answer] = {
    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")
    val questions = record.keys.filterNot(header.contains(_)).toList
    questions.map(question => Answer(
        contestant =  record(contestantColumn), 
        timeSent = record(timeSentColumn),
        question = question,
        names =  record(question).split(", ").map(_.trim).filter(_ != "")
      )
    )
  }

  def fromFile(filename: String): Seq[Answer] = {
    Csv.parse(filename).flatMap(fromRecord)
  }
}