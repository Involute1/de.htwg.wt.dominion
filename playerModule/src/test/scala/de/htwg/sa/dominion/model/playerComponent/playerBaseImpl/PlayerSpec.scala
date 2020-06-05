package de.htwg.sa.dominion.model.playerComponent.playerBaseImpl

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cards}
import org.scalatest.{Matchers, WordSpec}


class PlayerSpec extends WordSpec with Matchers {
  val deckLuca: List[Card] = List(Cards.copper, Cards.copper)
  val deckLuca1: List[Card] = List(Cards.copper)
  val stackerLuca: List[Card] = Nil
  val stackerLuca1: List[Card] = List(Cards.copper)
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handLuca2: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.silver)
  val handLuca1: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 1, 1, 0)
  val Luca2 = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 2, 2, 1)
  val Luca3 = Player("Luca", 0, deckLuca1, stackerLuca, handLuca1, 1, 1, 0)
  val Luca4 = Player("Luca", 0, deckLuca, stackerLuca, handLuca1, 1, 1, 0)
  val Luca5 = Player("Luca", 0, deckLuca, stackerLuca1, handLuca, 1, 1, 0)
  val Luca6 = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 1, 1, 5)
  val Luca7 = Player("Luca", 0, deckLuca, stackerLuca, handLuca2, 1, 1, 0)
  val Luca8 = Player("Luca", 0, Nil, handLuca, Nil, 1, 1, 0)
  val testList: List[Int] = List(0)
  "A Player" when {
    "new" should {
      "have a constructPlayerNameString method" in {
        Luca.constructPlayerNameString(Luca) should be ("Luca")
      }
      "have a constructPlayerDeckString method" in {
        Luca.constructPlayerDeckString(Luca) should be ("Copper (0)\nCopper (1)")
      }
      "have a constructPlayerStackerString method" in {
        Luca5.constructPlayerStackerString(Luca5) should be ("Copper (0)")
      }
      "have a constructPlayerHandString method" in {
        Luca.constructPlayerHandString(Luca) should be ("Copper (0)\nCopper (1)\nCopper (2)\nCopper (3)\nCopper (4)")
      }
      "have a updateActions method" in {
        Luca.updateActions(2, Luca) should be (Luca.copy(actions =  2))
      }
      "have a updateMoney method" in {
        Luca.updateMoney(1, Luca) should be (Luca.copy(money = 1))
      }
      "have a updateBuys method" in {
        Luca.updateBuys(2, Luca) should be (Luca.copy(buys = 2))
      }
      "have a checkForFirstSilver" in {
        Luca.checkForFirstSilver(Luca) should be (Luca)
        Luca7.checkForFirstSilver(Luca7) should be (Luca7.copy( money = 1))
      }
      "have a updateHand method " in {
        Luca.updateHand(1, Luca) should be (Luca3)
        Luca8.updateHand(1,Luca8) should be (Luca8.copy(handCards = List(Cards.copper), stacker = Nil, deck = List(Cards.copper,Cards.copper,Cards.copper,Cards.copper)))
      }
      "have a removeHandCardAddToStacker method" in {
        Luca4.removeHandCardAddToStacker(0, Luca4) should be (Luca5)
      }
      "have a trashHandCard method" in {
        Luca4.trashHandCard(0, Luca4) should be (Luca)
      }
      "have a discard method" in {
        Luca4.discard(testList, Luca4) should be (Luca5)
      }
      "have a calculatePlayerMoneyForBuy method" in {
        Luca.calculatePlayerMoneyForBuy(Luca) should be (Luca6)
      }
      "have a checkForTreasure method" in {
        Luca.checkForTreasure(Luca) should be (true)
      }
      "have a constructCellarTashString method" in {
        Luca.constructCellarTrashString(Luca) should be ("Copper (0)\nCopper (1)\nCopper (2)\nCopper (3)\nCopper (4)")
      }
      "have a removeCompleteHand method" in {
        Luca.removeCompleteHand(Luca, Luca.handCards.length-1) should be (Luca.copy(handCards = Nil, stacker = handLuca))
      }
      "have a moveAllCardsToDeckForScore method" in {
        Luca.moveAllCardsToDeckForScore(Luca) should be (Luca.copy(handCards = Nil, deck = List(Cards.copper, Cards.copper,Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)))
      }
      "have a calculateScore method" in {
        Luca.calculateScore(Luca) should be (0)
      }

    }
  }
}
