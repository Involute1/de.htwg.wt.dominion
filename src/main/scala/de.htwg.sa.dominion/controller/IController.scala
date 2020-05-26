package de.htwg.sa.dominion.controller
import de.htwg.sa.dominion.model.cardcomponent.Card
import de.htwg.sa.dominion.util.Observable

trait IController extends Observable {

  def eval(input: String)

  def undo(): Unit

  def redo(): Unit

  def save(): Unit

  def load(): Unit

  def getControllerMessage: String

  def setControllerMessage(message: String): Unit

  def getControllerStateAsString: String

  def getHelpPage(): Unit

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
}
