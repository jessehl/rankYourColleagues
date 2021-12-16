package rating


import scala.io.Source

object Main extends  App {

    
  val user = "E-mailadres"
  val timestamp = "Tijdstempel"
  val headers = Set(user, timestamp)
  
  class Answer(
    val username: String, 
    val timestamp: String,
    val names: Seq[String],
    val question: String
    ) {
    override def toString = s"(question: $question | username: $username | names: ${names.mkString(", ")})"
  }


  def getAnswers(record: Map[String, String]): Seq[Answer] = {
    val questions = record.keys.toList.filterNot(headers.contains(_))
    questions.map{question => 
      new Answer(
        username  = record(user), 
        timestamp = record(timestamp),
        question  = question,
        names     = record(question).split(", ").filterNot(_ == "")
      )
    }
  }


  def parseCsv(filename: String): Seq[Map[String, String]] = {
    val lines = Source.fromFile(filename, "utf-8").getLines.toList
    val header = lines.head.split("\t")
    lines.tail.map(line => header.zip(line.split("\t")).toMap)
  }


  // List all .tsv files.
  val files = new java.io.File("results").listFiles.map("results/" + _.getName).filter(name => name.contains(".tsv"))
  
  // Get all Answers.
  val answers: Seq[Answer] = files.map(parseCsv).flatten.flatMap(getAnswers(_))

  // Print the names which where answered most often.
  answers.map(_.names).flatten
    .groupBy(identity).mapValues(_.size)
    .toList.sortBy(- _._2)
    .take(10).foreach(println)
  
  // Print invalid Answers.
  val validAnswers = answers.filter(_.names.length < 3)
  answers.filter(_.names.length > 3).foreach(answer => println(s"invalid: $answer"))

  // Get points awarded per question, per name. 
  val pointsPerQuestionPerName = answers
    .groupBy(_.question)
    .mapValues(_.map(_.names).flatten.groupBy(identity).mapValues(_.length))
  
  // Get points awarded per user. 
  val users = answers.map(_.username).distinct
  val questions = pointsPerQuestionPerName.keys.toList 








}

