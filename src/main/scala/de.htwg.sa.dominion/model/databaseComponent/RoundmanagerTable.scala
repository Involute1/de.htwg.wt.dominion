package de.htwg.sa.dominion.model.databaseComponent

import slick.jdbc.MySQLProfile.api._

class RoundmanagerTable(tag: Tag) extends Table[(Int, Int, Int, Int, Boolean, Char, Int, Int, Int)](tag, "RoundmanagerTable"){
  def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)

  def numberOfPlayers = column[Int]("numberOfPlayers")

  def turn = column[Int]("turn")

  def emptyDeckCount = column[Int]("emptyDeckCount")

  def gameEnd = column[Boolean]("gameEnd")

  def roundStatus = column[Char]("roundStatus")

  def playerTurn = column[Int]("playerTurn")

  def scoreIdFKey = column[Int]("scoreIdFKey")

  def namesIdFKey = column[Int]("namesIdFKey")

  def * = (id, numberOfPlayers, turn, emptyDeckCount, gameEnd, roundStatus, playerTurn, scoreIdFKey, namesIdFKey)
}
