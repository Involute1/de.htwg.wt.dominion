package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.cardcomponent.Card
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager

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

  def toXML: Elem

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
}
