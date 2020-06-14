package de.htwg.sa.dominion.model.cardDatabaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cards, Cardtype}
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase
import de.htwg.sa.dominion.util.CardTables.{CardTable, DeckTable, HandCardsTable, PlayingCardsTable, StackerTable}
import slick.jdbc.SQLServerProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

class CardMsSqlDAO extends ICardDatabase {

  val cards = TableQuery[CardTable]
  val handCards = TableQuery[HandCardsTable]
  val playingDecks = TableQuery[PlayingCardsTable]
  val stackers = TableQuery[StackerTable]
  val playerDecks = TableQuery[DeckTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  override def create: Try[Boolean] = {
    Try {
      val setup = DBIO.seq(
        (cards.schema ++ handCards.schema ++ playingDecks.schema ++ stackers.schema ++ playerDecks.schema).createIfNotExists
      )
      db.run(setup)
      db.run(cards.delete)
      val insertActions = DBIO.seq(
        cards.map(c => (c.name, c.description, c.cardType, c.cost, c.money, c.vp, c.draw, c.buys, c.actions, c.additionalMoney)) ++= Seq(
          (Option(Cards.copper.cardName), Option(Cards.copper.cardDescription), Option(Cards.copper.cardType.toString),
            Option(Cards.copper.costValue), Option(Cards.copper.moneyValue), Option(Cards.copper.vpValue), Option(Cards.copper.cardDrawValue),
            Option(Cards.copper.additionalBuysValue), Option(Cards.copper.additionalActionsValue), Option(Cards.copper.additionalMoneyValue))
          ,
          (Option(Cards.silver.cardName), Option(Cards.silver.cardDescription), Option(Cards.silver.cardType.toString),
            Option(Cards.silver.costValue), Option(Cards.silver.moneyValue), Option(Cards.silver.vpValue), Option(Cards.silver.cardDrawValue),
            Option(Cards.silver.additionalBuysValue), Option(Cards.silver.additionalActionsValue), Option(Cards.silver.additionalMoneyValue))
          ,
          (Option(Cards.gold.cardName), Option(Cards.gold.cardDescription), Option(Cards.gold.cardType.toString),
            Option(Cards.gold.costValue), Option(Cards.gold.moneyValue), Option(Cards.gold.vpValue), Option(Cards.gold.cardDrawValue),
            Option(Cards.gold.additionalBuysValue), Option(Cards.gold.additionalActionsValue), Option(Cards.gold.additionalMoneyValue))
          ,
          (Option(Cards.estate.cardName), Option(Cards.estate.cardDescription), Option(Cards.estate.cardType.toString),
            Option(Cards.estate.costValue), Option(Cards.estate.moneyValue), Option(Cards.estate.vpValue), Option(Cards.estate.cardDrawValue),
            Option(Cards.estate.additionalBuysValue), Option(Cards.estate.additionalActionsValue), Option(Cards.estate.additionalMoneyValue))
          ,
          (Option(Cards.duchy.cardName), Option(Cards.duchy.cardDescription), Option(Cards.duchy.cardType.toString),
            Option(Cards.duchy.costValue), Option(Cards.duchy.moneyValue), Option(Cards.duchy.vpValue), Option(Cards.duchy.cardDrawValue),
            Option(Cards.duchy.additionalBuysValue), Option(Cards.duchy.additionalActionsValue), Option(Cards.duchy.additionalMoneyValue))
          ,
          (Option(Cards.province.cardName), Option(Cards.province.cardDescription), Option(Cards.province.cardType.toString),
            Option(Cards.province.costValue), Option(Cards.province.moneyValue), Option(Cards.province.vpValue), Option(Cards.province.cardDrawValue),
            Option(Cards.province.additionalBuysValue), Option(Cards.province.additionalActionsValue), Option(Cards.province.additionalMoneyValue))
          ,
          (Option(Cards.village.cardName), Option(Cards.village.cardDescription), Option(Cards.village.cardType.toString),
            Option(Cards.village.costValue), Option(Cards.village.moneyValue), Option(Cards.village.vpValue), Option(Cards.village.cardDrawValue),
            Option(Cards.village.additionalBuysValue), Option(Cards.village.additionalActionsValue), Option(Cards.village.additionalMoneyValue))
          ,
          (Option(Cards.festival.cardName), Option(Cards.festival.cardDescription), Option(Cards.festival.cardType.toString),
            Option(Cards.festival.costValue), Option(Cards.festival.moneyValue), Option(Cards.festival.vpValue), Option(Cards.festival.cardDrawValue),
            Option(Cards.festival.additionalBuysValue), Option(Cards.festival.additionalActionsValue), Option(Cards.festival.additionalMoneyValue))
          ,
          (Option(Cards.cellar.cardName), Option(Cards.cellar.cardDescription), Option(Cards.cellar.cardType.toString),
            Option(Cards.cellar.costValue), Option(Cards.cellar.moneyValue), Option(Cards.cellar.vpValue), Option(Cards.cellar.cardDrawValue),
            Option(Cards.cellar.additionalBuysValue), Option(Cards.cellar.additionalActionsValue), Option(Cards.cellar.additionalMoneyValue))
          ,
          (Option(Cards.mine.cardName), Option(Cards.mine.cardDescription), Option(Cards.mine.cardType.toString),
            Option(Cards.mine.costValue), Option(Cards.mine.moneyValue), Option(Cards.mine.vpValue), Option(Cards.mine.cardDrawValue),
            Option(Cards.mine.additionalBuysValue), Option(Cards.mine.additionalActionsValue), Option(Cards.mine.additionalMoneyValue))
          ,
          (Option(Cards.smithy.cardName), Option(Cards.smithy.cardDescription), Option(Cards.smithy.cardType.toString),
            Option(Cards.smithy.costValue), Option(Cards.smithy.moneyValue), Option(Cards.smithy.vpValue), Option(Cards.smithy.cardDrawValue),
            Option(Cards.smithy.additionalBuysValue), Option(Cards.smithy.additionalActionsValue), Option(Cards.smithy.additionalMoneyValue))
          ,
          (Option(Cards.remodel.cardName), Option(Cards.remodel.cardDescription), Option(Cards.remodel.cardType.toString),
            Option(Cards.remodel.costValue), Option(Cards.remodel.moneyValue), Option(Cards.remodel.vpValue), Option(Cards.remodel.cardDrawValue),
            Option(Cards.remodel.additionalBuysValue), Option(Cards.remodel.additionalActionsValue), Option(Cards.remodel.additionalMoneyValue))
          ,
          (Option(Cards.merchant.cardName), Option(Cards.merchant.cardDescription), Option(Cards.merchant.cardType.toString),
            Option(Cards.merchant.costValue), Option(Cards.merchant.moneyValue), Option(Cards.merchant.vpValue), Option(Cards.merchant.cardDrawValue),
            Option(Cards.merchant.additionalBuysValue), Option(Cards.merchant.additionalActionsValue), Option(Cards.merchant.additionalMoneyValue))
          ,
          (Option(Cards.workshop.cardName), Option(Cards.workshop.cardDescription), Option(Cards.workshop.cardType.toString),
            Option(Cards.workshop.costValue), Option(Cards.workshop.moneyValue), Option(Cards.workshop.vpValue), Option(Cards.workshop.cardDrawValue),
            Option(Cards.workshop.additionalBuysValue), Option(Cards.workshop.additionalActionsValue), Option(Cards.workshop.additionalMoneyValue))
          ,
          (Option(Cards.gardens.cardName), Option(Cards.gardens.cardDescription), Option(Cards.gardens.cardType.toString),
            Option(Cards.gardens.costValue), Option(Cards.gardens.moneyValue), Option(Cards.gardens.vpValue), Option(Cards.gardens.cardDrawValue),
            Option(Cards.gardens.additionalBuysValue), Option(Cards.gardens.additionalActionsValue), Option(Cards.gardens.additionalMoneyValue))
          ,
          (Option(Cards.market.cardName), Option(Cards.market.cardDescription), Option(Cards.market.cardType.toString),
            Option(Cards.market.costValue), Option(Cards.market.moneyValue), Option(Cards.market.vpValue), Option(Cards.market.cardDrawValue),
            Option(Cards.market.additionalBuysValue), Option(Cards.market.additionalActionsValue), Option(Cards.market.additionalMoneyValue))
        )
      )
      db.run(insertActions)
      true
    }
  }

  override def read(): Unit = ???

  override def update(playingDecksList: Option[List[List[Card]]], handCardsList: Option[List[Card]], stackerCardsList: Option[List[Card]],
                      deckCardsList: Option[List[Card]], playerId: Option[Int]): Try[Boolean] = {
    Try {
      if (playingDecksList.isDefined) {
        db.run(playingDecks.delete)
        for (deck <- playingDecksList.head) {
          val deckSize = deck.size
          val card = deck.head.cardName
          val cardIdToInsert: Seq[Int] = Await.result(db.run(cards.filter(_.name === card).map(_.cardId).result), Duration(1, TimeUnit.SECONDS))
          val deckInsert = playingDecks.map(c => (c.cardFk, c.amount)) += (cardIdToInsert.head, Option(deckSize))
          db.run(deckInsert)
        }
        true
      } else {
        val deletePlayerCardsQuery = DBIO.seq(handCards.filter(_.playerFk === playerId.head).delete,
          stackers.filter(_.playerFk === playerId.head).delete,
          playerDecks.filter(_.playerFk === playerId.head).delete)
        db.run(deletePlayerCardsQuery)
        for (card <- handCardsList.head) {
          val cardIdToInsert: Seq[Int] = Await.result(db.run(cards.filter(_.name === card.cardName).map(_.cardId).result), Duration(1, TimeUnit.SECONDS))
          val handCardsInsert = handCards.map(c => (c.playerFk, c.cardFk)) += (playerId.head, cardIdToInsert.head)
          db.run(handCardsInsert)
        }
        for (card <- stackerCardsList.head) {
          val cardIdToInsert: Seq[Int] = Await.result(db.run(cards.filter(_.name === card.cardName).map(_.cardId).result), Duration(1, TimeUnit.SECONDS))
          val stackerCardsInsert = stackers.map(c => (c.playerFk, c.cardFk)) += (playerId.head, cardIdToInsert.head)
          db.run(stackerCardsInsert)
        }
        for (card <- deckCardsList.head) {
          val cardIdToInsert: Seq[Int] = Await.result(db.run(cards.filter(_.name === card.cardName).map(_.cardId).result), Duration(1, TimeUnit.SECONDS))
          val deckCardsInsert = playerDecks.map(c => (c.playerFk, c.cardFk)) += (playerId.head, cardIdToInsert.head)
          db.run(deckCardsInsert)
        }
        true
      }
    }
  }

  override def delete: Try[Boolean] = {
    ???
  }
}
