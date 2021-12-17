package ranking 

object Score {

  type Contestant = String 
  type Points = Int

  /**
    * Returns the total points earned per contestant. 
    */
  def compute(answers: Seq[Answer]): List[(Contestant, Points)] = {

    // Raise error if one contestant answered a question more than once.
    // This constraint should be enforced by Google Forms (but better safe than sorry).
    val duplicateAnswers = answers.groupBy(_.contestant)
      .map(_._2.groupBy(_.question)).map(_.values)
      .flatten.filter(_.length != 1)
    assert(duplicateAnswers.isEmpty, s"found duplicate answers: ${duplicateAnswers.mkString(", ")}")
    
    // Filter out invalid Answers (i.e. more than 3 names).
    val (validAnswers, invalidAnswers) = answers.partition(_.names.length <= 3)
    invalidAnswers.foreach(answer => println(s"invalid answer: $answer"))
  
    // Get points awarded per question, per name. 
    val pointsPerQuestionPerName = validAnswers
        .groupBy(_.question)
        .mapValues(_.map(_.names).flatten.groupBy(identity)
        .mapValues(_.length))

    // Return points earned per Contestant. 
    validAnswers
      .groupBy(_.contestant) 
      .mapValues(_.flatMap(answer => 
        answer.names.map(pointsPerQuestionPerName(answer.question).getOrElse(_, 0))).sum
        )
      .toList
  }

}