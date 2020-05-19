package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.cardcomponent.{Card, Cards}
import org.scalatest.{Matchers, WordSpec}

import scala.collection.immutable


class PlayerSpec extends WordSpec with Matchers {
  val deckLuca: List[Card] = List(Cards.copper, Cards.copper)
  val deckLuca1: List[Card] = List(Cards.copper)
  val stackerLuca: List[Card] = Nil
  val stackerLuca1: List[Card] = List(Cards.copper)
  val handLuca: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val handLuca1: List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper)
  val Luca = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 1, 1, 0,0)
  val Luca2 = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 2, 2, 1,0)
  val Luca3 = Player("Luca", 0, deckLuca1, stackerLuca, handLuca1, 1, 1, 0,0)
  val Luca4 = Player("Luca", 0, deckLuca, stackerLuca, handLuca1, 1, 1, 0,0)
  val Luca5 = Player("Luca", 0, deckLuca, stackerLuca1, handLuca, 1, 1, 0,0)
  val Luca6 = Player("Luca", 0, deckLuca, stackerLuca, handLuca, 1, 1, 5,0)
  val testList: List[Int] = List(0)
  "A Player" when {
    "new" should {
      "have a constructPlayerNameString method" in {
        Luca.constructPlayerNameString() should be ("Luca")
      }
      "have a constructPlayerDeckString method" in {
        Luca.constructPlayerDeckString() should be ("Copper (0)\nCopper (1)")
      }
      "have a constructPlayerStackerString method" in {
        Luca.constructPlayerStackerString() should be ("")
      }
      "have a constructPlayerHandString method" in {
        Luca.constructPlayerHandString() should be ("Copper (0)\nCopper (1)\nCopper (2)\nCopper (3)\nCopper (4)")
      }
      "have a updateActions method" in {
        Luca.updateActions(2) should be (Luca2)
      }
      "have a updateMoney method" in {
        Luca.updateMoney(1) should be (Luca2)
      }
      "have a updateBuys method" in {
        Luca.updateBuys(2) should be (Luca2)
      }
      "have a checkForFirstSilver" in {
        Luca.checkForFirstSilver() should be (Luca)
      }
      "have a updateHand method " in {
        Luca.updateHand(1, Luca) should be (Luca3)
      }
      "have a removeHandCardAddToStacker method" in {
        Luca4.removeHandCardAddToStacker(0) should be (Luca5)
      }
      "have a trashHandCard method" in {
        Luca4.trashHandCard(0) should be (Luca)
      }
      "have a discard method" in {
        Luca4.discard(testList) should be (Luca5)
      }
      "have a calculatePlayerMoneyForBuy method" in {
        Luca.calculatePlayerMoneyForBuy should be (Luca6)
      }
      "have a checkForTreasure method" in {
        Luca.checkForTreasure() should be (true)
      }
      "have a constructCellarTashString method" in {
        Luca.constructCellarTrashString() should be ("Copper (0)\nCopper (1)\nCopper (2)\nCopper (3)\nCopper (4)")
      }
    }
  }
}
