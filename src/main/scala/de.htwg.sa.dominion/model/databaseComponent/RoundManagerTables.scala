package de.htwg.sa.dominion.model.databaseComponent

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

object RoundManagerTables {
  class RoundmanagerTable(tag: Tag) extends Table[(Int, Option[Int], Option[Int], Option[Int], Option[Boolean], Option[String], Option[Int], Option[Int], Option[Int])](tag, "ROUNDMANAGER") {
    def id: Rep[Int] = column[Int]("ROUND_MANAGER_ID", O.PrimaryKey, O.AutoInc)

    def numberOfPlayers: Rep[Option[Int]] = column[Option[Int]]("NUMBER_OF_PLAYERS")

    def turn: Rep[Option[Int]] = column[Option[Int]]("TURN")

    def emptyDeckCount: Rep[Option[Int]] = column[Option[Int]]("EMPTY_DECK_COUNT")

    def gameEnd: Rep[Option[Boolean]] = column[Option[Boolean]]("GAME_END")

    def roundStatus: Rep[Option[String]] = column[Option[String]]("ROUND_STATUS")

    def playerTurn: Rep[Option[Int]] = column[Option[Int]]("PLAYER_TURN")

    def scoreIdFKey: Rep[Option[Int]] = column[Option[Int]]("SCORE_ID_FKEY")

    def namesIdFKey: Rep[Option[Int]] = column[Option[Int]]("NAMES_ID_FKEY")

    def * : ProvenShape[(Int, Option[Int], Option[Int], Option[Int], Option[Boolean], Option[String], Option[Int], Option[Int], Option[Int])] = (id, numberOfPlayers, turn, emptyDeckCount, gameEnd, roundStatus, playerTurn, scoreIdFKey, namesIdFKey)
  }

  class NameTable(tag: Tag) extends Table[(Int, Option[String], Option[String], Option[String], Option[String], Option[String])](tag, "ROUNDMANAGER_NAMES") {
    def namesId: Rep[Int] = column[Int]("NAMES_ID", O.PrimaryKey, O.AutoInc)

    def player1Name: Rep[Option[String]]= column[Option[String]]("PLAYER_1_NAME")

    def player2Name: Rep[Option[String]]= column[Option[String]]("PLAYER_2_NAME")

    def player3Name: Rep[Option[String]]= column[Option[String]]("PLAYER_3_NAME")

    def player4Name: Rep[Option[String]]= column[Option[String]]("PLAYER_4_NAME")

    def player5Name: Rep[Option[String]] = column[Option[String]]("PLAYER_5_NAME")

    def * : ProvenShape[(Int, Option[String], Option[String], Option[String], Option[String], Option[String])] = (namesId, player1Name, player2Name, player3Name, player4Name, player5Name)
  }

  class ScoreTable(tag: Tag) extends Table[(Int, Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])](tag, "ROUNDMANAGER_SCORE") {
    def scoreId: Rep[Int]  = column[Int]("SCORE_ID", O.PrimaryKey, O.AutoInc)

    def player1Score: Rep[Option[Int]] = column[Option[Int]]("PLAYER_1_SCORE")

    def player2Score: Rep[Option[Int]] = column[Option[Int]]("PLAYER_2_SCORE")

    def player3Score: Rep[Option[Int]] = column[Option[Int]]("PLAYER_3_SCORE")

    def player4Score: Rep[Option[Int]] = column[Option[Int]]("PLAYER_4_SCORE")

    def player5Score: Rep[Option[Int]] = column[Option[Int]]("PLAYER_5_SCORE")

    def * : ProvenShape[(Int, Option[Int], Option[Int], Option[Int], Option[Int], Option[Int])] = (scoreId, player1Score, player2Score, player3Score, player4Score, player5Score)
  }
}
