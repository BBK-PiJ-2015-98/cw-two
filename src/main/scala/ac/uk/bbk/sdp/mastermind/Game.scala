package ac.uk.bbk.sdp.mastermind

class Game(inputMethod: InputMethod, gameEngine: GameEngine, testMode: Boolean) {

  def finishGame(won: Boolean): Unit = {
    if (won) {
      println("Congratulations! You won the game!")
    } else {
      println("You did not solve the puzzle. Too bad.")
    }

    print("Enter Y for another game or anything else to quit:")
    if (inputMethod.hasInput() && inputMethod.getInput() == "Y") {
      createAndPlayGame(false)
    } else {
      println("Good bye")
    }
  }

  def createGameFlow(displayWelcomeMessage: Boolean) =
    new GameFlow(inputMethod, gameEngine, displayWelcomeMessage, testMode)

  def createAndPlayGame(displayWelcomeMessage: Boolean) = {
    val gameFlow = createGameFlow(displayWelcomeMessage)
    finishGame(gameFlow.playGame())
  }

  def runGames() = createAndPlayGame(true)
}

trait GameComponent {
  val game: Game
}

trait ActiveGameComponent extends GameComponent {
  this: InputMethodComponent with GameEngineComponent =>
    override val game = new Game(inputMethod, gameEngine, testMode = true)
}
