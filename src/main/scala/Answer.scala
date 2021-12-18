package ranking 




case class Answer(
  val contestant: Score.Contestant, 
  val timeSent: String,
  val question: Score.Question,
  val names: Score.Names
  ){
  override def toString = s"(question: $question | contestant: $contestant | names: ${names.mkString(", ")})"
}



object Answers {

  val contestantColumn = "E-mailadres"
  val timeSentColumn = "Tijdstempel"
  val header = List(contestantColumn, timeSentColumn)

  def fromRecord(record: Map[String, String]): Seq[Answer] = {
    val questions: Seq[Score.Question] = 
      record.keys.filterNot(header.contains(_)).toList

    questions.map(question => Answer(
        contestant =  record(contestantColumn), 
        timeSent = record(timeSentColumn),
        question = question,
        names =  record(question).split(", ").map(_.trim).filter(_ != "").distinct
      )
    )
  }

  def fromFile(filename: String): Seq[Answer] = {
    Tsv.parse(filename).flatMap(fromRecord)
  }
}