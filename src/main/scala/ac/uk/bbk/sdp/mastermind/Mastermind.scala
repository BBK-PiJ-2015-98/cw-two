package ac.uk.bbk.sdp.mastermind

object Mastermind extends KeyboardInputMethodComponent
                  with ImplementedGameEngineComponent
                  with ActiveGameComponent {
  def main(args: Array[String]) = game.runGames()
}
