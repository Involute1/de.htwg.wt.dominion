package de.htwg.sa.dominion.model.databaseComponent

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

object RoundManagerTables {
  class RoundmanagerTable(tag: Tag) extends Table[(Int, Int, Int, Int, Boolean, String, Int, Int, Int)](tag, "ROUNDMANAGER") {
    def id: Rep[Int] = column[Int]("ROUND_MANAGER_ID", O.PrimaryKey, O.AutoInc)

    def numberOfPlayers: Rep[Int] = column[Int]("NUMBER_OF_PLAYERS")

    def turn: Rep[Int] = column[Int]("TURN")

    def emptyDeckCount: Rep[Int] = column[Int]("EMPTY_DECK_COUNT")

    def gameEnd: Rep[Boolean] = column[Boolean]("GAME_END")

    def roundStatus: Rep[String] = column[String]("ROUND_STATUS")

    def playerTurn: Rep[Int] = column[Int]("PLAYER_TURN")

    def scoreIdFKey: Rep[Int] = column[Int]("SCORE_ID_FKEY")

    def namesIdFKey: Rep[Int] = column[Int]("NAMES_ID_FKEY")

    def * : ProvenShape[(Int, Int, Int, Int, Boolean, String, Int, Int, Int)] = (id, numberOfPlayers, turn, emptyDeckCount, gameEnd, roundStatus, playerTurn, scoreIdFKey, namesIdFKey)
  }

  class NameTable(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "ROUNDMANAGER_NAMES") {
    def namesId: Rep[Int] = column[Int]("NAMES_ID", O.PrimaryKey, O.AutoInc)

    def player1Name: Rep[String]= column[String]("PLAYER_1_NAME")

    def player2Name: Rep[String]= column[String]("PLAYER_2_NAME")

    def player3Name: Rep[String]= column[String]("PLAYER_3_NAME")

    def player4Name: Rep[String]= column[String]("PLAYER_4_NAME")

    def player5Name: Rep[String] = column[String]("PLAYER_5_NAME")

    def * : ProvenShape[(Int, String, String, String, String, String)] = (namesId, player1Name, player2Name, player3Name, player4Name, player5Name)
  }

  class ScoreTable(tag: Tag) extends Table[(Int, Int, Int, Int, Int, Int)](tag, "ROUNDMANAGER_SCORE") {
    def scoreId: Rep[Int]  = column[Int]("SCORE_ID", O.PrimaryKey, O.AutoInc)

    def player1Score: Rep[Int] = column[Int]("PLAYER_1_SCORE")

    def player2Score: Rep[Int] = column[Int]("PLAYER_2_SCORE")

    def player3Score: Rep[Int] = column[Int]("PLAYER_3_SCORE")

    def player4Score: Rep[Int] = column[Int]("PLAYER_4_SCORE")

    def player5Score: Rep[Int] = column[Int]("PLAYER_5_SCORE")

    def * : ProvenShape[(Int, Int, Int, Int, Int, Int)] = (scoreId, player1Score, player2Score, player3Score, player4Score, player5Score)
  }
}
