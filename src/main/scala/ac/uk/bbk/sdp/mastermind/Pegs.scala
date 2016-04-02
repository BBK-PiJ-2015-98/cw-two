package ac.uk.bbk.sdp.mastermind

object Pegs {

  sealed abstract class Peg(val pegColour: String)
  sealed abstract class ColouredPeg(val colour: String) extends Peg(colour)
  sealed abstract class ResultPeg(val colour: String) extends Peg(colour)

  case object BluePeg extends ColouredPeg("B")
  case object GreenPeg extends ColouredPeg("G")
  case object OrangePeg extends ColouredPeg("O")
  case object PurplePeg extends ColouredPeg("P")
  case object RedPeg extends ColouredPeg("R")
  case object YellowPeg extends ColouredPeg("Y")

  case object BlackPeg extends ResultPeg("Black")
  case object WhitePeg extends ResultPeg("White")

  def getColouredPegFromCode(code: Char): Option[ColouredPeg] = {
    code match {
      case 'B' => Some(BluePeg)
      case 'G' => Some(GreenPeg)
      case 'O' => Some(OrangePeg)
      case 'P' => Some(PurplePeg)
      case 'R' => Some(RedPeg)
      case 'Y' => Some(YellowPeg)
      case _ => None
    }
  }
}
