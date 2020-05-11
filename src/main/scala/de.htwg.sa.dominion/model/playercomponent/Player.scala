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
    // TODO needs to be tested
    val deckString = ""
    this.deck.foreach(x => deckString.appended(x.cardName + "(" + x + ")\n"))
    deckString
  }

  override def constructPlayerStackerString(): String = {
    val stackerString = ""
    this.stacker.foreach(x => stackerString.appended(x.cardName + "(" + x + ")\n"))
    stackerString
  }

  override def constructPlayerHandString(): String = {
    val handStringList: List[String] = for (card <- this.handCards) yield card.cardName + " (" + ")\n"
    val playherHandString: String = handStringList.mkString("")
    playherHandString.toString
  }


}
