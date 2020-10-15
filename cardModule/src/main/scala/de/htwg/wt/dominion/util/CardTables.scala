package de.htwg.wt.dominion.util

import slick.lifted.{ForeignKeyQuery, ProvenShape}
import slick.jdbc.MySQLProfile.api._

object CardTables {

  val cards = TableQuery[CardTable]

  class CardTable(tag: Tag) extends Table[(Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])](tag, "CARD") {
    def cardId: Rep[Int] = column[Int]("CARD_ID", O.PrimaryKey, O.AutoInc)

    def name: Rep[Option[String]] = column[Option[String]]("NAME", O.SqlType("NVARCHAR(30)"))

    def description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION", O.SqlType("NVARCHAR(100)"))

    def cardType: Rep[Option[String]] = column[Option[String]]("TYPE", O.SqlType("NVARCHAR(30)"))

    def cost: Rep[Option[Int]] = column[Option[Int]]("COST")

    def money: Rep[Option[Int]] = column[Option[Int]]("MONEY")

    def vp: Rep[Option[Int]] = column[Option[Int]]("VP")

    def draw: Rep[Option[Int]] = column[Option[Int]]("DRAW")

    def buys: Rep[Option[Int]] = column[Option[Int]]("BUYS")

    def actions: Rep[Option[Int]] = column[Option[Int]]("ACTIONS")

    def additionalMoney: Rep[Option[Int]] = column[Option[Int]]("ADDITIONAL_MONEY")

    def * : ProvenShape[(Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])]
    = (cardId, name, description, cardType, cost, money, vp, draw, buys, actions, additionalMoney)

  }

  class PlayingCardsTable(tag: Tag) extends Table[(Int, Int, Option[Int])](tag, "CARD_PLAYING_DECKS") {
    def id: Rep[Int] = column[Int]("PLAYING_DECK_ID", O.PrimaryKey, O.AutoInc)

    def cardFk: Rep[Int] = column[Int]("CARD_FKEY")

    def amount: Rep[Option[Int]] = column[Option[Int]]("AMOUNT")

    def * : ProvenShape[(Int, Int, Option[Int])] = (id, cardFk, amount)

    def playingCardsCardFk: ForeignKeyQuery[CardTable, (Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = foreignKey("PLAYING_CARDS_CARD_FK", cardFk, cards)(_.cardId, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  }

  class TrashCardsTable(tag: Tag) extends Table[(Int, Int)](tag, "CARD_TRASH") {
    def trashId: Rep[Int] = column[Int]("TRASH_ID", O.PrimaryKey, O.AutoInc)

    def cardFk: Rep[Int] = column[Int]("CARD_FKEY")

    def * : ProvenShape[(Int, Int)] = (trashId, cardFk)

    def trashCardsCardFk: ForeignKeyQuery[CardTable, (Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = foreignKey("TRASH_CARDS_CARD_FK", cardFk, cards)(_.cardId, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  }

  class HandCardsTable(tag: Tag) extends Table[(Int, Int, Int)](tag, "CARD_PLAYER_HAND_CARDS") {
    def handCardsId: Rep[Int] = column[Int]("HAND_CARD_ID", O.PrimaryKey, O.AutoInc)

    def playerFk: Rep[Int] = column[Int]("PLAYER")

    def cardFk: Rep[Int] = column[Int]("CARD_FKEY")

    def * : ProvenShape[(Int, Int, Int)] = (handCardsId, playerFk, cardFk)

    def handCardsCardFk: ForeignKeyQuery[CardTable, (Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = foreignKey("HAND_CARDS_CARD_FK", cardFk, cards)(_.cardId, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    //def handCardsPlayerFk = foreignKey("PLAYER_FK", playerFk, )
  }

  class DeckTable(tag: Tag) extends Table[(Int, Int, Int)](tag, "CARD_PLAYER_DECK") {
    def deckId: Rep[Int] = column[Int]("DECK_ID", O.PrimaryKey, O.AutoInc)

    def playerFk: Rep[Int] = column[Int]("PLAYER")

    def cardFk: Rep[Int] = column[Int]("CARD_FKEY")

    def * : ProvenShape[(Int, Int, Int)] = (deckId, playerFk, cardFk)

    def deckCardFk: ForeignKeyQuery[CardTable, (Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = foreignKey("DECK_CARD_FK", cardFk, cards)(_.cardId, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    //def handCardsPlayerFk = foreignKey("PLAYER_FK", playerFk, )
  }

  class StackerTable(tag: Tag) extends Table[(Int, Int, Int)](tag, "CARD_PLAYER_STACKER") {
    def stackerId: Rep[Int] = column[Int]("STACKER_ID", O.PrimaryKey, O.AutoInc)

    def playerFk: Rep[Int] = column[Int]("PLAYER")

    def cardFk: Rep[Int] = column[Int]("CARD_FKEY")

    def * : ProvenShape[(Int, Int, Int)] = (stackerId, playerFk, cardFk)

    def stackerCardFk: ForeignKeyQuery[CardTable, (Int, Option[String], Option[String], Option[String], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = foreignKey("STACKER_CARD_FK", cardFk, cards)(_.cardId, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    //def handCardsPlayerFk = foreignKey("PLAYER_FK", playerFk, )
  }

}
