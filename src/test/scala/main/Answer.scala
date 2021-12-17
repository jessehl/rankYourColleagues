package ranking

import org.junit.Test


class TestAnswer {

  @Test def `from File should identify all answers`= {
    val answers = Answers.fromFile("src/test/results/example.tsv")
    assert(answers.length == 14)
  }

}

