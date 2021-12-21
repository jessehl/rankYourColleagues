package ranking



object Main extends  App {


  // List all .tsv files.
  val filenames = new java.io.File("results")
    .listFiles.map(file => s"results/${file.getName}")
    .filter(_.takeRight(4) == ".tsv")
  

  // Get all Answers.
  val answers: Seq[Answer] = filenames.flatMap(Answers.fromFile)


  // Print total number of Answers.
  println(s"number of answers: ${answers.length}")


  // Print total number of names mentioned.
  println(s"number of names: ${answers.map(_.names.length).sum}")


  // Print Answers with less than 3 names.
  answers.filter(_.names.length < 3)
    .foreach(answer => println(s"less than 3 names answered: ${answer}"))


  // Print how often was answered with each name.
  answers.flatMap(_.names)
    .groupBy(identity)
    .mapValues(_.size)
    .toList.sortBy{case (name, count) => - count}
    .foreach(nameCount => println(s"mentioned often: $nameCount"))


  // Print how often the players answered with their own name.
  answers.map{answer => 
      val name    = answer.contestant.split("@")(0).replace(".", " ").toLowerCase
      val self    = answer.names.map(_.toLowerCase).contains(name)
      (name, self)
    }
    .groupBy{case (name, self) => name}
    .mapValues(_.filter{case (name, self) => self == true}.length)
    .toList.sortBy(- _._2)
    .foreach(nameCount => println(s"mentioned own name: $nameCount"))
    

  // Check whether all forms were sent around the same time. 
  answers.map(answer => (answer.timeSent, answer.contestant))
    .distinct.sortBy(_._1)
    .foreach(sentForm => println(s"submitted form:  $sentForm"))


  // Print duplicate answers. 
  val duplicates = Score.duplicates(answers) 
  duplicates.foreach(answer => s"found duplicate answer: $answer")
  
  
  // Compute the score if there are no duplicates.
  if(duplicates.isEmpty) Score.compute(answers)
    .sortBy(_._2)
    .foreach{case (contestant, points) => println(s"points: ${contestant}:${points}")}

}

