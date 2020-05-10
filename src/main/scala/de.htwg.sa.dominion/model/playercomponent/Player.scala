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
    val handString = ""
    this.handCards.foreach(x => handString.appended(x.cardName + "(" + x + ")\n"))
    handString
  }
  override def createPlayer(playerCount: Int, names: List[String]): List[Player] = {
    val players = new ListBuffer[Player]
    for (i <- 0 until playerCount) {
      players += new Player(names(i), i + 1,Deck.startDeck,Nil,Nil,1,1,0,0)
    }
    val Players: List[Player] = players.toList
    Players
  }

}
