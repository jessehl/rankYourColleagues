package ranking

import org.junit.Test
import scala.util.Try

class TestScore {

    val timeSent = "2020-01-01 00:00:00"
    val answer0 = Answer("person.0@example.nl", timeSent, "question 0", List())
    val answer1 = Answer("person.1@example.nl", timeSent, "question 1", List("Person 1"))
    val answer2 = Answer("person.2@example.nl", timeSent, "question 1", List("Person 1"))
    val answer3 = Answer("person.3@example.nl", timeSent, "question 1", List("Person 1", "Person 2", "Person 3"))
    val answer4 = Answer("person.3@example.nl", timeSent, "question 2", List("Person 1", "Person 2", "Person 3"))
    val answer5 = Answer("person.4@example.nl", timeSent, "question 1", List("Person 1", "Person 2", "Person 3", "Person 4"))



@Test def `test with no Contestants` = {
    assert(Score.compute(List.empty[Answer]).isEmpty)
}

@Test def `test with no Names mentioned` = {
    assert(Score.compute(List(answer0)).head._2 == 0)
}

@Test def `test with one Contestant` = {
    val scores = Score.compute(List(answer1))
    assert(scores.length == 1)
    assert(scores.head._2 == 1)
}

@Test def `test with two Contestants` = {
    val scores = Score.compute(List(answer1, answer2))
    assert(scores.length == 2)
    assert(scores.map(_._2) == List(2, 2))
}

@Test def `test with three Contestants` = {
    val scores = Score.compute(List(answer1, answer2, answer3))
    assert(scores.length == 3)
    assert(scores.map(_._2).sorted == List(3, 3, 5).sorted)
    assert(scores.filter(_._2 == 5).head._1 == "person.3@example.nl")
}


@Test def `test with four Contestants and two Questions` = {
    val scores = Score.compute(List(answer1, answer2, answer3, answer4))
    assert(scores.length == 3)
    assert(scores.map(_._2).sorted == List(3, 3, 8).sorted)
    assert(scores.filter(_._2 == 8).head._1 == "person.3@example.nl")
}


@Test def `test that invalid Answer is filtered out` = {
    assert(Score.compute(List(answer5)).isEmpty)
}

@Test def `test that no duplicate Answers are reported when they aren't there` = { 
    assert(Score.duplicates(List(answer1, answer2, answer3, answer4, answer5)).isEmpty)
}

@Test def `test that duplicate Answers are spotted` = {
    assert(Score.duplicates(List(answer1, answer1)).nonEmpty)
    assert(Score.duplicates(List(answer4, answer4)).nonEmpty)
}


}