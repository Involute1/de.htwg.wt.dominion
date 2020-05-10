package de.htwg.sa.dominion.model.roundmanagerComponent

import de.htwg.sa.dominion.model.RoundmanagerInterface
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.cardcomponent.{Card, CardName, Cards}
import de.htwg.sa.dominion.model.playercomponent.Player

case class Roundmanager(players: List[Player], numberOfPlayers: Int, turn: Int, decks: List[List[Card]],
                        emptyDeckCount: Int, gameEnd: Boolean, score: List[(Int, String)]) extends RoundmanagerInterface {

  override def createPlayingDecks(cardName: CardName, roundmanager: Roundmanager): Roundmanager = {
    cardName match {
      case moneyCard if moneyCard == CardName.COPPER || moneyCard == CardName.SILVER || moneyCard == CardName.GOLD
      => constructPlayingCardDeck(cardName, 100, roundmanager)
      case victoryPointCard if victoryPointCard == CardName.ESTATE || victoryPointCard == CardName.DUCHY || victoryPointCard == CardName.PROVINCE
      => constructPlayingCardDeck(cardName, 12, roundmanager)
      case _ => constructPlayingCardDeck(cardName, 10, roundmanager)
    }
  }

  private def constructPlayingCardDeck(card: CardName, amount: Int, roundmanager: Roundmanager): Roundmanager = {
    // TODO Find better way for this pattern matching
    val deck: List[Card] = List.fill(amount)(card.toString match {
      case "Copper" => Cards.copper
      case "Silver" => Cards.silver
      case "Gold" => Cards.gold
      case "Estate" => Cards.estate
      case "Duchy" => Cards.duchy
      case "Province" => Cards.province
      case "Village" => Cards.village
      case "Festival" => Cards.festival
      case "Cellar" => Cards.cellar
      case "Mine" => Cards.mine
      case "Smithy" => Cards.smithy
      case "Remodel" => Cards.remodel
      case "Merchant" => Cards.merchant
      case "Workshop" => Cards.workshop
      case "Gardens" => Cards.gardens
      case "Market" => Cards.market
    })

    val decksNew: List[List[Card]] = List(deck)
    if (roundmanager.decks.nonEmpty) {
      val decksNew2: List[List[Card]] = List.concat(roundmanager.decks, decksNew)
      roundmanager.copy(decks = decksNew2)
    } else {
      roundmanager.copy(decks = decksNew)
    }
  }

  private def createPlayers() : Roundmanager {

  }

}
