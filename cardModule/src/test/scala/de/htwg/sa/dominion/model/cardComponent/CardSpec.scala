package de.htwg.sa.dominion.model.cardComponent

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cardtype}
import org.scalatest.{Matchers, WordSpec}

class CardSpec extends WordSpec with Matchers {
  val copper: Card = Card("Copper", "1 Money", Cardtype.MONEY, 0, 1, 0, 0, 0, 0, 0)

  "A Card" when {
    "new"should {
      "have a  constructCardNameString method" in {
        copper.constructCardNameString() should be ("Copper")
      }
      "have a constructCardInformationString method" in {
        copper.constructCardInformationString should be ("Cardname: Copper\nCarddescription: 1 Money\nCost: 0 Money\nMoney worth: 1\nVictory Points worth: 0\nAllows you to have 0 Actions, 0 Buys,0 Additional Money and draws 0 more cards")
      }
    }
  }
}
