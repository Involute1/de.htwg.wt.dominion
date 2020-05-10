package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.cardcomponent.Card
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager

trait RoundmanagerInterface {

  def createPlayingDecks(cardName: CardName): Roundmanager

  def createPlayerList(): Roundmanager

  def namesEqualPlayer(): Boolean

  def updateNumberOfPlayer(numberOfPlayers: Int): Roundmanager

  def updateListNames(name: String): Roundmanager

  def constructControllerAskNameString: String

  def shuffle(deck: List[Card]): List[Card]

}
