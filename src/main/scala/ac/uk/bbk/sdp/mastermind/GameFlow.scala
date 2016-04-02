package ac.uk.bbk.sdp.mastermind

import scala.annotation.tailrec

import ac.uk.bbk.sdp.mastermind.Pegs.{Peg, ResultPeg, ColouredPeg}

class GameFlow(inputMethod: InputMethod, gameEngine: GameEngine, displayWelcomeMessage: Boolean, testMode: Boolean) {

  type GuessedPegs = Seq[Option[ColouredPeg]]
  type ResultPegs = Seq[ResultPeg]
  type GuessHistory = Seq[(GuessedPegs, ResultPegs)]

  import GameFlow._

  if (displayWelcomeMessage) {
    println(
      s"""Welcome to Mastermind.

This is a text version of the classic board game Mastermind.
The computer will think of a secret code.
The code consists of 4 colored pegs.
The pegs may be one of six colors: blue, green , orange, purple, red, or yellow.
A color may appear more than once in the code.

You try to guess what colored pegs are in the code and what order they are in
After making a guess the result will be displayed.
A result consists of a black peg for each peg you have exactly correct (color and position) in your guess.
For each peg in the guess that is the correct color, but is out of position, you get a white peg.

Only the first letter of the color is displayed. B for Blue, R for Red, and so forth.
When entering guesses you only need to enter the first character of the color as a capital letter.

You have ${gameEngine.MaxNumberOfGuesses} to guess the answer or you lose the game.\n"""
    )
  }

  println("Generating secret code ....")
  val secretCode = gameEngine.generateSecretCode()

  printSecretCode()
  printNumberOfGuessesLeft(gameEngine.MaxNumberOfGuesses)

  def printSecretCode() =
    if (testMode) {
      println(s"The secret code is ${pegsToString(secretCode)}")
    }

  def printNumberOfGuessesLeft(numberOfGuessesLeft: Int) =
    println(s"You have ${numberOfGuessesLeft} guesses left.\n")

  def printResultsTable(guessHistory: GuessHistory) = {
    guessHistory.foreach { case (guess, result ) =>
      println(
        s"${pegsToString(guess.flatten)} Result: ${pegsToString(result, " ")}"
      )
    }
  }

  def playGame(): Boolean = {

    @tailrec
    def getGuess(numberOfGuessesLeft: Int, guessHistory: GuessHistory): Boolean = {

      if (gameEngine.canGuess(numberOfGuessesLeft)) {
        println("What is your next guess?\nType in the characters for your guess and press enter.")
        print("Enter guess:")

        val guessInput = inputMethod.getInput()
        val guessedPegs = gameEngine.getGuessedPegs(guessInput)
        val nextGuessLeft = numberOfGuessesLeft - 1
        if (gameEngine.isGuessValid(guessedPegs)) {

          val result = gameEngine.getFeedback(secretCode, guessedPegs)
          val historyWithNewResult = guessHistory :+ (guessedPegs, result)

          printSecretCode()
          printResultsTable(historyWithNewResult)

          if (gameEngine.isGuessEqualToSecretCode(secretCode, guessedPegs)) {
            true
          } else {
            printNumberOfGuessesLeft(nextGuessLeft)
            getGuess(nextGuessLeft, historyWithNewResult)
          }
        } else {
          getGuess(numberOfGuessesLeft, guessHistory)
        }
      } else {
        false
      }
    }

    getGuess(gameEngine.MaxNumberOfGuesses, Nil)
  }
}

// Companion object having helper functions
object GameFlow {
  def pegsToString(pegs: Seq[Peg], separator: String = ""): String =
    if (pegs.isEmpty) "No pegs"
    else pegs.map( peg => peg.pegColour).mkString(separator)
}
