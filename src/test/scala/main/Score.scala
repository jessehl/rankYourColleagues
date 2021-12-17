package ranking

import org.junit.Test
import scala.util.Try

class TestScore {

    val ts              = "2020-01-01 00:00:00"
    val answer1         = Answer("person1", ts, "question1", List("person1"))
    val answer2         = Answer("person2", ts, "question1", List("person1"))
    val answer3         = Answer("person3", ts, "question1", List("person1", "person2", "person3"))
    val answer4         = Answer("person3", ts, "question2", List("person1", "person2", "person3"))
    val answerInvalid   = Answer("sillyperson", ts, "question1", List("person1", "person2", "person3", "person4"))


@Test def `test with no contestants` = {
    assert(Score.compute(List.empty[Answer]).isEmpty)
}

@Test def `test with one contestant` = {
    val scores = Score.compute(List(answer1))
    assert(scores.length == 1)
    val points: Score.Points = scores.head._2
    assert(points == 1)
}

@Test def `test with two contestants` = {
    val scores = Score.compute(List(answer1, answer2))
    assert(scores.length == 2)
    val points: List[Score.Points] = scores.map(_._2)
    assert(points.sorted == List(2, 2))
}

@Test def `test with three contestants` = {
    val scores = Score.compute(List(answer1, answer2, answer3))
    assert(scores.length == 3)
    val points: List[Score.Points] = scores.map(_._2)
    assert(points.sorted == List(3, 3, 5).sorted)
    assert(scores.filter(_._2 == 5).head._1 == "person3")
}


@Test def `test with four contestants and two questions` = {
    val scores = Score.compute(List(answer1, answer2, answer3, answer4))
    assert(scores.length == 3)
    val points: List[Score.Points] = scores.map(_._2)
    assert(points.sorted == List(3, 3, 8).sorted)
    assert(scores.filter(_._2 == 8).head._1 == "person3")
}

@Test def `test that duplicate answers are spotted` = {
    assert(Try(Score.compute(List(answer1, answer1))).isFailure)
}


@Test def `test that invalid answer is filtered out` = {
    assert(Score.compute(List(answerInvalid)).isEmpty)
}



    

}