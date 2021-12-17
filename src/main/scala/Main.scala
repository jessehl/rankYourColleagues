package ranking

import scala.io.Source


object Main extends  App {


  // List all .tsv files.
  val files = new java.io.File("results")
    .listFiles.map(file => s"results/${file.getName}")
    .filter(_.takeRight(4) == ".tsv")
  

  // Get all Answers.
  val answers: Seq[Answer] = files.flatMap(Answers.fromFile)


  // Print how often was answered with each name.
  answers.map(_.names).flatten
    .groupBy(identity).mapValues(_.size)
    .toList.sortBy(- _._2)
    .foreach(ans => println(s"mentioned often: $ans"))


  // Print the how often the players answered with their own name.
  val answeredWithSelf = answers.map{
    answer => 
      val contestantName = answer.contestant.split("@")(0).replace(".", " ").toLowerCase
      val answeredOwnName = answer.names.map(_.toLowerCase).contains(contestantName)
      (contestantName, answeredOwnName)
    }
  answeredWithSelf.groupBy(_._1).mapValues(_.filter(_._2 == true).length)
    .toList.sortBy(- _._2).foreach(ans => println(s"mentioned own name: $ans"))
    

  // Check whether all forms were sent around the same time. 
  answers.map(answer => (answer.timeSent, answer.contestant)).distinct
    .sortBy(_._1).foreach(ans => println(s"submitted form:  $ans"))


  // Get and print the score!
  Score.compute(answers)
    .sortBy(_._2)
    .foreach(score => println(score))

}

