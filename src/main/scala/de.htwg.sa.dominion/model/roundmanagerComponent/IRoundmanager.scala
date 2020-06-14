package de.htwg.sa.dominion.model.roundmanagerComponent

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.CardName.CardName
import de.htwg.sa.dominion.model.playerComponent.IPlayer
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import play.api.libs.json.JsValue

import scala.xml.Elem

trait IRoundmanager {

  def createPlayingDecks(cardName: CardName): Roundmanager

  def initializePlayersList(idx: Int): Roundmanager

  def namesEqualPlayer(): Boolean

  def updateNumberOfPlayer(numberOfPlayers: Int): Roundmanager

  def updateListNames(name: String): Roundmanager

  def constructControllerAskNameString: String

  def shuffle(deck: List[Card]): List[Card]

  def drawCard(index: Int): Roundmanager

  def checkForGameEnd(): Boolean

  def actionPhase(input: String): Roundmanager

  def constructRoundermanagerStateString: String

  def getNumberOfPlayers: Int

  def checkIfActionPhaseDone: Boolean

  def buyPhase(input: String): Roundmanager

  def checkForNextPlayer: Boolean

  def getCurrentPlayerTurn: Int

  def getNameListSize: Int

  def constructScoreString: String

  def getCurrentPlayerName: String

  def getCurrentPlayerActions: Int

  def getCurrentPlayerMoney: Int

  def getCurrentPlayerBuys: Int

  def getCurrentPlayerDeck: List[Card]

  def getCurrentPlayerHand: List[Card]

  def getPlayingDecks: List[List[Card]]

  def getScore: List[(String, Int)]

  def getTurn: Int

  def getCurrentInstance: Roundmanager

  def toJson: JsValue

  def fromJson(jsValue: JsValue): IRoundmanager

  def toXml: Elem

  def fromXml(node: scala.xml.Node): IRoundmanager
}
