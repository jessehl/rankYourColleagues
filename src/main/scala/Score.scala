package ranking 

import scala.collection.{MapView, View}

object Score {

  // E.g. 'Person 1'
  type Name = String 
  type Names = Seq[Name]

  // E.g. 'person.1@example.nl'
  type Contestant = String 

  type Points = Int 

  // E.g. 'Who is most likely to ... ?'
  type Question = String 


  /* Returns the total Points earned per Contestant. */
  def compute(answers: Seq[Answer]): List[(Contestant, Points)] = {

    // Google Forms should enforce one answer per question, but better safe than sorry.
    assertNoDuplicates(answers)

    // Filter out invalid Answers (i.e. more than 3 names).
    val (validAnswers, invalidAnswers) = answers.partition(_.names.length <= 3)
    invalidAnswers.foreach(answer => println(s"invalid answer: $answer"))
    
    // Get points awarded per Question, per Name. 
    val points: MapView[Question, MapView[Name, Points]] = 
      validAnswers
        .groupBy(answer => answer.question)
        .mapValues(answers => computePointsPerQuestion(answers))

    def computePointsForContestant(answers: Seq[Answer]): Points = answers
      .flatMap(answer => answer.names.map(name => points(answer.question)(name)))
      .sum

    // Return points earned per Contestant. 
    validAnswers
      .groupBy(answer => answer.contestant) 
      .mapValues(answers => computePointsForContestant(answers))
      .toList
  }



  /* Raises error when multiple contestants answered the same question more than once. */
  private def assertNoDuplicates(answers: Seq[Answer]) = {
    val duplicates = answers
      .groupBy(_.contestant)
      .mapValues(_.groupBy(_.question))
      .flatMap(_._2.values)
      .filter(answers => answers.length != 1)
      .flatten

    assert(duplicates.isEmpty, s"duplicate answers found: ${duplicates}")
  }

  private def computePointsPerQuestion(answers: Seq[Answer]): MapView[Name, Points] = {
    answers
      .flatMap(answer => answer.names)
      .groupBy(identity)
      .mapValues(names => names.length)
    }



}