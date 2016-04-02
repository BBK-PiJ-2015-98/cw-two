package ac.uk.bbk.sdp.mastermind

import ac.uk.bbk.sdp.mastermind.Pegs.{BlackPeg, WhitePeg, YellowPeg, RedPeg, BluePeg}
import org.specs2.mutable.Specification

class GameEngineSpec extends Specification {

  val gameEngine = new ImplementedGameEngine()

  "GameEngine" >> {
    "generateSecretCode" should {
      "generate 4 random pegs" in {
        val firstResult = gameEngine.generateSecretCode()
        val secondResult = gameEngine.generateSecretCode()
        firstResult shouldNotEqual secondResult
      }
    }

    "getGuessedPegs" >> {
      "when number of pegs is valid and" >> {
        "all guesses are valid then it should return a list of valid pegs" in {
          val expectedPegs = Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Nil
          val result = gameEngine.getGuessedPegs("BBBB")
          result shouldEqual expectedPegs
        }

        "some guesses are invalid then it should return a list of mixed valid/invalid pegs" in {
          val expectedPegs = Some(BluePeg) :: Some(BluePeg) :: None :: None :: Nil
          val result = gameEngine.getGuessedPegs("BBAA")
          result shouldEqual expectedPegs
        }
      }
    }

    "isGuessValid" should {
      "return true when valid guess" in {
        val pegs = Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Nil
        gameEngine.isGuessValid(pegs) should beTrue
      }
      "return false when guess is not the required number of pegs" in {
        val pegs = Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Nil
        gameEngine.isGuessValid(pegs) should beFalse
      }
      "return false when there is an unknown peg" in {
        val pegs = Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: None :: Nil
        gameEngine.isGuessValid(pegs) should beFalse
      }
    }

    "isGuessEqualToSecretCode" should {
      "return true when they have exactly the same pegs in order" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(BluePeg) :: Some(RedPeg) :: Nil
        gameEngine.isGuessEqualToSecretCode(secretCode, guess) should beTrue
      }
      "return false when they have exactly the same pegs but not in order" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(RedPeg) :: Some(BluePeg) :: Nil
        gameEngine.isGuessEqualToSecretCode(secretCode, guess) should beFalse
      }
      "return false when they have different pegs" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(RedPeg) :: Nil
        gameEngine.isGuessEqualToSecretCode(secretCode, guess) should beFalse
      }
    }

    "canGuess" should {
      "return true when there are still guesses left" in {
        gameEngine.canGuess(1) should beTrue
      }
      "return false when there are no guesses left" in {
        gameEngine.canGuess(0) should beFalse
      }
    }

    "getFeedback" should {
      "return no pegs when no colour and no position was correctly guessed" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(YellowPeg) :: Some(YellowPeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(Nil)

      }
      "return a white peg when colour match but no position was correctly guessed" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(YellowPeg) :: Some(BluePeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(WhitePeg :: Nil)
      }
      "return a black when colour and position was correctly guessed" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(BluePeg) :: Some(YellowPeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(BlackPeg :: Nil)
      }
      "do not give White peg for a guess that already has a Black peg result" in {
        val secretCode = BluePeg :: BluePeg :: RedPeg :: RedPeg :: Nil
        val guess = Some(BluePeg) :: Some(BluePeg) :: Some(BluePeg) :: Some(RedPeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(BlackPeg :: BlackPeg :: BlackPeg :: Nil)
      }
      "do not give White peg for a guess that already has a White peg result" in {
        val secretCode = RedPeg :: BluePeg :: RedPeg :: BluePeg :: Nil
        val guess = Some(YellowPeg) :: Some(RedPeg) :: Some(YellowPeg) :: Some(RedPeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(WhitePeg :: Nil)
      }
      "return black pegs when colour and position was correctly guessed for all items" in {
        val secretCode = BluePeg :: RedPeg :: Nil
        val guess = Some(BluePeg) :: Some(RedPeg) :: Nil
        gameEngine.getFeedback(secretCode, guess) should beEqualTo(BlackPeg :: BlackPeg :: Nil)
      }
    }
  }
}
