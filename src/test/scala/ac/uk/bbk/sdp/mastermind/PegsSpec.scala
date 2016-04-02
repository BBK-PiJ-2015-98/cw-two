package ac.uk.bbk.sdp.mastermind

import ac.uk.bbk.sdp.mastermind.Pegs._
import org.specs2.mutable.Specification

class PegsSpec extends Specification {

  "Pegs" >> {
    "getColouredPegFromCode" should {
       "return valid Blue peg" in {
         Pegs.getColouredPegFromCode('B') shouldEqual Some(BluePeg)
       }
      "return valid Green peg" in {
        Pegs.getColouredPegFromCode('G') shouldEqual Some(GreenPeg)
      }
      "return valid Orange peg" in {
        Pegs.getColouredPegFromCode('O') shouldEqual Some(OrangePeg)
      }
      "return valid Purple peg" in {
        Pegs.getColouredPegFromCode('P') shouldEqual Some(PurplePeg)
      }
      "return valid Red peg" in {
        Pegs.getColouredPegFromCode('R') shouldEqual Some(RedPeg)
      }
      "return valid Yellow peg" in {
        Pegs.getColouredPegFromCode('Y') shouldEqual Some(YellowPeg)
      }
      "return None for unkown code" in {
        Pegs.getColouredPegFromCode('A') shouldEqual None
      }
    }
  }
}
