package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.cardcomponent.Card
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.playercomponent.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager

trait RoundmanagerInterface {

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
}
