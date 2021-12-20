package ranking 


object Form {

  // E.g. 'Person 1'
  type Name = String 
  type Names = Seq[Name]

  // E.g. '2020-01-01 00:00:00'
  type TimeSent = String
  val timeSentColumn = "Tijdstempel"

  // E.g. 'person.1@example.nl'
  type Contestant = String
  val contestantColumn = "E-mailadres"

  // E.g. 'Who is most likely to ... ?'
  type Question = String 
  
}