package ac.uk.bbk.sdp.mastermind

import scala.annotation.tailrec

import ac.uk.bbk.sdp.mastermind.Pegs._

trait GameEngine {

  val RowSize = 4
  val MaxNumberOfGuesses = 12

  def generateSecretCode(): Seq[ColouredPeg]
  def getGuessedPegs(guess: String): Seq[Option[ColouredPeg]]
  def isGuessValid(guessedPegs: Seq[Option[ColouredPeg]]): Boolean
  def isGuessEqualToSecretCode(secretCode: Seq[ColouredPeg], guessedPegs: Seq[Option[ColouredPeg]]): Boolean
  def canGuess(numberOfGuessesLeft: Int): Boolean
  def getFeedback(secretCode: Seq[ColouredPeg], guessedPegs: Seq[Option[ColouredPeg]]): Seq[ResultPeg]
}

class ImplementedGameEngine extends GameEngine {

  override def generateSecretCode(): Seq[ColouredPeg] = {

    import scala.util.Random

    // Creating a list of elements, same as List(BluePeg(), GreenPeg(), ...)
    val validChoices =
      BluePeg :: GreenPeg :: OrangePeg :: PurplePeg :: RedPeg :: YellowPeg :: Nil

    Random.shuffle(validChoices).take(RowSize)
  }

  override def getGuessedPegs(guess: String): Seq[Option[ColouredPeg]] = {
    val characters = guess.toCharArray.toSeq
    characters.map(Pegs.getColouredPegFromCode)
  }

  override def isGuessValid(guessedPegs: Seq[Option[ColouredPeg]]): Boolean = {
    def isValidPeg(peg: Option[ColouredPeg]): Boolean = {
      peg match {
        case Some(_) => true
        case _ => false
      }
    }
    guessedPegs.size == RowSize && guessedPegs.forall(isValidPeg)
  }

  override def isGuessEqualToSecretCode(secretCode: Seq[ColouredPeg], guessedPegs: Seq[Option[ColouredPeg]]): Boolean = {
     return guessedPegs.flatten == secretCode
  }

  override def canGuess(numberOfGuessesLeft: Int): Boolean = numberOfGuessesLeft > 0

  override def getFeedback(secretCode: Seq[ColouredPeg], guessedPegs: Seq[Option[ColouredPeg]]): Seq[ResultPeg] = {

    def doesSecretCodeHaveColouredPeg(guessedPeg: ColouredPeg): Boolean =
      secretCode.contains(guessedPeg)

    def doesSecretCodeHaveColouredPegAtPosition(guessedPeg: ColouredPeg, position: Int): Boolean =
      secretCode(position) == guessedPeg

    def wasColouredPegAlreadyAddedToFeedback(peg: ColouredPeg, blackPegs: Set[ColouredPeg]): Boolean = blackPegs.contains(peg)

    @tailrec
    def buildResult(guessedPegsIter: Iterator[Option[ColouredPeg]], blackPegs: Set[ColouredPeg], whitePegs: Set[ColouredPeg], position: Int, result: Seq[ResultPeg]): Seq[ResultPeg] = {
       if (guessedPegsIter.hasNext) {

         val currentGuessedPeg = guessedPegsIter.next()

         val (resultDependingOnMatchFound, newBlackPegs, newWhitePegs) = currentGuessedPeg.map { peg =>
           if (doesSecretCodeHaveColouredPegAtPosition(peg, position)) {
             (result :+ BlackPeg, blackPegs + peg, whitePegs)
           } else if (doesSecretCodeHaveColouredPeg(peg) &&
                      !wasColouredPegAlreadyAddedToFeedback(peg, blackPegs) &&
                      !wasColouredPegAlreadyAddedToFeedback(peg, whitePegs)
           ) {
             (result :+ WhitePeg, blackPegs, whitePegs + peg)
           } else {
             (result, blackPegs, whitePegs)
           }
         }.getOrElse((result, blackPegs, whitePegs))

         buildResult(guessedPegsIter, newBlackPegs, newWhitePegs, position + 1, resultDependingOnMatchFound)
       } else {
         result
       }
    }

    val finalResult = if (secretCode.size != guessedPegs.size) {
      Nil
    } else {
      buildResult(guessedPegs.iterator, Set.empty, Set.empty, 0, Nil)
    }

    finalResult
  }
}

trait GameEngineComponent {
  val gameEngine: GameEngine
}

trait ImplementedGameEngineComponent extends GameEngineComponent {
  override val gameEngine = new ImplementedGameEngine()
}
