package ranking 


case class Answer(
  val username: String, 
  val timestamp: String,
  val names: Seq[String],
  val question: String
  )
{
  override def toString = s"(question: $question | username: $username | names: ${names.mkString(", ")})"
}


object Answers {

  val userColumn = "E-mailadres"
  val timestampColumn = "Tijdstempel"
  val header = List(userColumn, timestampColumn)

  def fromRecord(record: Map[String, String]): Seq[Answer] = {
    val questions = record.keys.filterNot(header.contains(_)).toList
    questions.map{questionString => 
      new Answer(
        username =  record(userColumn), 
        timestamp = record(timestampColumn), 
        names =  record(questionString).split(", ").map(_.trim).filter(_ != ""), 
        question = questionString
      )
    }
  }

  def fromFile(filename: String): Seq[Answer] = {
    Csv.parse(filename).flatMap(fromRecord)
  }
}