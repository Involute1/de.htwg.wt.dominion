package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager

trait RoundmanagerInterface {

  def createPlayingDecks(cardName: CardName): Roundmanager

}
