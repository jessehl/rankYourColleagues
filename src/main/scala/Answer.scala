package ranking 


case class Answer(
  val contestant: Form.Contestant, 
  val timeSent: Form.TimeSent,
  val question: Form.Question,
  val names: Form.Names
  ){
  override def toString = s"(question: $question | contestant: $contestant | names: ${names.mkString(", ")})"
}


object Answers {

  val header = List(Form.contestantColumn, Form.timeSentColumn)

  def fromRecord(record: Map[String, String]): Seq[Answer] = {
    val questions: Seq[Form.Question] = 
      record.keys.filterNot(header.contains(_)).toList

    questions.map(question => Answer(
        contestant =  record(Form.contestantColumn), 
        timeSent = record(Form.timeSentColumn),
        question = question,
        names =  record(question).split(", ").map(_.trim).filter(_ != "").distinct
      )
    )
  }

  def fromFile(filename: String): Seq[Answer] = {
    Tsv.parse(filename).flatMap(fromRecord)
  }
}