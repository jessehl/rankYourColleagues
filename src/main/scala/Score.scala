package ranking 

object Score {


  type User = String 
  type Value = Int


  def compute(answers: Seq[Answer]): List[(User, Value)] = {

    // Filter out invalid Answers.
    val (validAnswers, invalidAnswers) = answers.partition(_.names.length < 3)
    invalidAnswers.foreach(answer => println(s"invalid: $answer"))

    // Get points awarded per question, per name. 
    val pointsPerQuestionPerName = validAnswers
        .groupBy(_.question) 
        .mapValues(_.map(_.names).flatten.groupBy(identity).mapValues(_.length))
    
    // Get points awarded per user. 
    val users = answers
        .groupBy(_.username) 
        .mapValues(_.flatMap(answer => answer.names.map(pointsPerQuestionPerName(answer.question).getOrElse(_, 0))).sum)

    // Sort from high to low.
    users.toList.sortBy(_._2)
  }

}