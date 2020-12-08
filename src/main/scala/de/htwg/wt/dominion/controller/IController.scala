package de.htwg.wt.dominion.controller

import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.wt.dominion.util.Observable

import scala.swing.Publisher
import scala.swing.event.Event

trait IController extends Publisher {

  def eval(input: String)

  def undo(): Unit

  def redo(): Unit

  def save(): Unit

  def load(): Unit

  def getControllerMessage: String

  def setControllerMessage(message: String): Unit

  def getControllerStateAsString: String

  def getCurrentPlayerTurn: Int

  def getNameListSize: Int

  def getCurrentPlayerName: String

  def getCurrentPlayerActions: Int

  def getCurrentPlayerMoney: Int

  def getCurrentPlayerBuys: Int

  def getCurrentPlayerDeck: List[Card]

  def getCurrentPhaseAsString: String

  def getCurrentPlayerHand: List[Card]

  def getPlayingDecks: List[List[Card]]

  def getScore: List[(String, Int)]

  def getTurn: Int

  def toHTML: String
}

class EvalEvent extends Event
