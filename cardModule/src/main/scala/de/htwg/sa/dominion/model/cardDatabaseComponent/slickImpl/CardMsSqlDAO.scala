package de.htwg.sa.dominion.model.cardDatabaseComponent.slickImpl

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Cards, Cardtype}
import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase
import de.htwg.sa.dominion.util.CardTables.{CardTable, DeckTable, HandCardsTable, PlayingCardsTable, StackerTable}
import slick.jdbc.SQLServerProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._

import scala.util.Try

class CardMsSqlDAO extends ICardDatabase {

  val cards = TableQuery[CardTable]
  val handCards = TableQuery[HandCardsTable]
  val playingDecks = TableQuery[PlayingCardsTable]
  val stackers = TableQuery[StackerTable]
  val playerDecks = TableQuery[DeckTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  override def create: Try[Boolean] = {
    // TODO insert Cards
    Try {
      val setup = DBIO.seq((cards.schema ++ handCards.schema ++ playingDecks.schema ++ stackers.schema ++ playerDecks.schema).createIfNotExists)
      db.run(setup)
      true
    }
  }

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
