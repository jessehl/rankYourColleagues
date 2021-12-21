package ranking 

import scala.collection.MapView
import scala.collection.View

object Score {



  /* Returns the total Points earned per Contestant. */
  def compute(answers: Seq[Answer]): List[(Contestant, Points)] = {

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

    // Return Points earned per Contestant. 
    validAnswers
      .groupBy(answer => answer.contestant) 
      .mapValues(answers => computePointsForContestant(answers))
      .toList
  }


  /* Returns the Answers where a Contestant answered a Question more than once. 
  Google Forms should enforce one Answer per Question, but better safe than sorry.*/
  def duplicates(answers: Seq[Answer]): View[Answer] = {
    answers
      .groupBy(_.contestant)
      .mapValues(_.groupBy(_.question))
      .flatMap(_._2.values)
      .filter(answers => answers.length != 1)
      .flatten
  }

  private def computePointsPerQuestion(answers: Seq[Answer]): MapView[Name, Points] = {
    answers
      .flatMap(answer => answer.names)
      .groupBy(identity)
      .mapValues(names => names.length)
    }



}
