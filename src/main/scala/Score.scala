package ranking 

import scala.collection.{MapView, View}

object Score {

  // e.g. 'Person 1'
  type Name = String 
  type Names = Seq[Name]

  // e.g. 'person.1@example.nl'
  type Contestant = String 

  type Points = Int 

  // e.g. 'Who is most likely to ... ?'
  type Question = String 


  /* Returns the total Points earned per Contestant. */
  def compute(answers: Seq[Answer]): List[(Contestant, Points)] = {

    // Spot duplicate Answers (answering the same question more than once). 
    val duplicateAnswers: View[Answer] =
      answers
        .groupBy(_.contestant)
        .mapValues(_.groupBy(_.question))
        .flatMap(_._2.values)
        .filter(answers => answers.length != 1)
        .flatten

    // Raise error if there are duplicates. 
    // This constraint should be enforced by Google Forms (but better safe than sorry).
    assert(duplicateAnswers.isEmpty, s"duplicate answers found: ${duplicateAnswers}")
    
    // Filter out invalid Answers (i.e. more than 3 names).
    val (validAnswers, invalidAnswers) = answers.partition(_.names.length <= 3)
    invalidAnswers.foreach(answer => println(s"invalid answer: $answer"))
  
    // Get points awarded per Question, per Name. 
    val points: MapView[Question, MapView[Name, Points]] = 
      validAnswers
        .groupBy(answer => answer.question)
        .mapValues(
          answers => answers.flatMap(
            answer => answer.names
          ).groupBy(identity)
        .mapValues(names => names.length))

    // Return points earned per Contestant. 
    validAnswers
      .groupBy(_.contestant) 
      .mapValues(
        answers => answers.flatMap(
          answer => answer.names.map(
            name => points(answer.question)(name)
          )
        ).sum
      )
      .toList
  }

}