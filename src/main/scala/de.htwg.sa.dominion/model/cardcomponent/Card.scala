package de.htwg.sa.dominion.model.cardcomponent

import de.htwg.sa.dominion.model.cardcomponent.Cardtype.Cardtype
import de.htwg.sa.dominion.model.{CardInterface, cardcomponent}

case class Card(cardName: String, cardDescription: String, cardType: Cardtype, costValue: Int, moneyValue: Int, vpValue: Int
               , cardDrawValue: Int, additionalBuysValue: Int, additionalActionsValue: Int, additionalMoneyValue: Int) extends CardInterface {

  override def constructCardNameString(): String = {
    this.cardName
  }

  override def constructCardInformationString: String = {
    val cardInfoString = "Cardname: " + this.cardName + "\nCarddescription: " + this.cardDescription + "\nCost: " +
      + this.costValue + " Money\n" + "Money worth: " + this.moneyValue + "\nVictory Points worth: " + this.vpValue + "\n" +
        "Allows you to have " + this.additionalActionsValue + " Actions, " + this.additionalBuysValue + " Buys," + this.additionalMoneyValue + " " +
        "Additional Money and draws " + this.cardDrawValue + " more cards"
    cardInfoString
  }
}

object Cardtype extends Enumeration {
  type Cardtype = Value
  val MONEY, VICTORYPOINT, KINGDOM = Value
}

object CardName extends Enumeration {
  type CardName = Value
  val COPPER: cardcomponent.CardName.Value = Value("Copper")
  val SILVER: cardcomponent.CardName.Value = Value("Silver")
  val GOLD: cardcomponent.CardName.Value = Value("Gold")
  val ESTATE: cardcomponent.CardName.Value = Value("Estate")
  val DUCHY: cardcomponent.CardName.Value = Value("Duchy")
  val PROVINCE: cardcomponent.CardName.Value = Value("Province")
  val VILLAGE: cardcomponent.CardName.Value = Value("Village")
  val FESTIVAL: cardcomponent.CardName.Value = Value("Festival")
  val CELLAR: cardcomponent.CardName.Value = Value("Cellar")
  val MINE: cardcomponent.CardName.Value = Value("Mine")
  val SMITHY: cardcomponent.CardName.Value = Value("Smithy")
  val REMODEL: cardcomponent.CardName.Value = Value("Remodel")
  val MERCHANT: cardcomponent.CardName.Value = Value("Merchant")
  val WORKSHOP: cardcomponent.CardName.Value = Value("Workshop")
  val GARDENS: cardcomponent.CardName.Value = Value("Gardens")
  val MARKET: cardcomponent.CardName.Value = Value("Market")
}

object Cards {
  val copper: Card = Card("Copper", "1 Money", Cardtype.MONEY, 0, 1, 0, 0, 0, 0, 0)
  val silver: Card = Card("Silver", "2 Money", Cardtype.MONEY, 3, 2, 0, 0, 0, 0, 0)
  val gold: Card = Card("Gold", "3 Money", Cardtype.MONEY, 6, 3, 0, 0, 0, 0, 0)

  val estate: Card = Card("Estate", "1 Victory Point", Cardtype.VICTORYPOINT, 2, 0, 1, 0, 0, 0, 0)
  val duchy: Card = Card("Duchy", "3 Victory Points", Cardtype.VICTORYPOINT, 5, 0, 3, 0, 0, 0, 0)
  val province: Card = Card("Province", "5 Victory Points", Cardtype.VICTORYPOINT, 8, 0, 6, 0, 0, 0, 0)

  val village: Card = Card("Village", "+1 Card, +2 Actions", Cardtype.KINGDOM, 3, 0, 0, 1, 0, 2, 0)
  val festival: Card = Card("Festival", "+2 Actions, +1 Buy, +2 Money", Cardtype.KINGDOM, 5, 0, 0, 0, 1, 2, 2)
  val cellar: Card = Card("Cellar", "+1 Action, Discard any number of cards, then draw that many", Cardtype.KINGDOM, 2, 0, 0, 0, 0, 1, 0)
  val mine: Card = Card("Mine", "You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to 3 more than it"
    , Cardtype.KINGDOM, 5, 0, 0, 0, 0, 0, 0)
  val smithy: Card = Card("Smithy", "+3 Cards", Cardtype.KINGDOM, 4, 0, 0, 3, 0, 0, 0)
  val remodel: Card = Card("Remodel", "Trash a card from your hand. Gain a card costing up to 2 more than it"
    , Cardtype.KINGDOM, 4, 0, 0, 0, 0, 0, 0)
  val merchant: Card = Card("Merchant", "+1 Card, +1 Action, The first time you play a Silver this turn, +1 Money"
    , Cardtype.KINGDOM, 3, 0, 0, 1, 0, 1, 0)
  val workshop: Card = Card("Workshop", "Gain a card costing up to 4", Cardtype.KINGDOM, 3, 0, 0, 0, 0, 0, 0)
  val gardens: Card = Card("Gardens", "Worth 1 WinningPoint per 10 cards you have(round down)", Cardtype.VICTORYPOINT, 4, 0, 0, 0, 0, 0, 0)
  val market: Card = Card("Market", "+1 Card, +1 Action, +1 Buy, +1 Money", Cardtype.KINGDOM, 5, 0, 0, 1, 1, 1, 1)

}

object Deck {
  val startDeck : List[Card] = List(Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper, Cards.copper,
    Cards.copper, Cards.estate, Cards.estate, Cards.estate)
}

