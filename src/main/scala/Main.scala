package ranking

import scala.io.Source


object Main extends  App {


  // List all .tsv files.
  val files = new java.io.File("results")
    .listFiles.map("results/" + _.getName)
    .filter(_.contains(".tsv"))
  

  // Get all Answers.
  val answers: Seq[Answer] = files.flatMap(Answers.fromFile)

  // Print the names which where answered most often.
  answers.map(_.names).flatten
    .groupBy(identity).mapValues(_.size)
    .toList.sortBy(- _._2)
    .take(10).foreach(println)

  // Get the score!
  println(Score.compute(answers))


}

