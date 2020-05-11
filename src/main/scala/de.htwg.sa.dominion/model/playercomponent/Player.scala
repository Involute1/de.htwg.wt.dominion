package de.htwg.sa.dominion.model.playercomponent

import de.htwg.sa.dominion.model.PlayerInterface
import de.htwg.sa.dominion.model.cardcomponent.{Card, Deck}

import scala.collection.mutable.ListBuffer

case class Player(name: String, value: Int, deck: List[Card], stacker: List[Card], handCards: List[Card],
                  actions: Int, buys: Int, money: Int, victoryPoint: Int) extends PlayerInterface {

  override def constructPlayerNameString(): String = {
    this.name
  }

  override def constructPlayerDeckString(): String = {
    val deckStringList: List[String] = for ((card, idx) <- this.deck.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playerDeckString: String = deckStringList.mkString("\n")
    playerDeckString.toString

  }

  override def constructPlayerStackerString(): String = {
    val stackerStringList: List[String] = for ((card, idx) <- this.stacker.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playerStackerString: String = stackerStringList.mkString("\n")
    playerStackerString.toString

  }

  override def constructPlayerHandString(): String = {
    val handStringList: List[String] = for ((card, idx) <- this.handCards.zipWithIndex) yield card.cardName + " (" + idx + ")"
    val playherHandString: String = handStringList.mkString("\n")
    playherHandString.toString
  }


}
