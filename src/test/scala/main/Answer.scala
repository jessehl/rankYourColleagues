package ranking

import org.junit.Test


class TestAnswer {

  val answers = Answers.fromFile("src/test/results/example.tsv")

  @Test def `fromFile should identify all answers`= {
    assert(answers.length == 14)
  }

  @Test def `each question should be listed for every contestant` = {
  assert(answers.filter(_.question == "Welke collega's komen het vaakst te laat?").length == 2)
  }

  @Test def `every answer of a contestant should be there` = {
    assert(answers.filter(_.contestant == "person.1@example.nl").length == 7)
  }

}

