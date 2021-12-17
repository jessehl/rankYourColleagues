package ranking

import org.junit.Test


class TestScore {
    val ts = "2021-01-01 00:00:00"
    val answers = List(Answer("person1", ts, List("person1"), "question1"))


@ Test def `test number of users` = {
    assert(Score.compute(answers).length == 1)
    assert(Score.compute(answers ::: answers).length == 1)
}


    

    

}