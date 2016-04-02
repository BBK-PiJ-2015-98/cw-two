package ac.uk.bbk.sdp.mastermind

import java.util.Scanner

trait InputMethod {
  def hasInput(): Boolean
  def getInput(): String
}

class KeyboardInputMethod extends InputMethod {

  val input = new Scanner(System.in)

  override def hasInput(): Boolean = input.hasNext()

  override def getInput(): String = input.next()
}

trait InputMethodComponent {
  val inputMethod: InputMethod
}

trait KeyboardInputMethodComponent extends InputMethodComponent {
   override val inputMethod = new KeyboardInputMethod()
}
