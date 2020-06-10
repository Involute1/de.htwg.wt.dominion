package de.htwg.sa.dominion.model.databaseComponent

import slick.jdbc.MySQLProfile.api._

class NameTable(tag: Tag) extends Table[(Int, Char, Char, Char, Char, Char)](tag, "NameTable")  {
  def namesId = column[Int]("namesId", O.PrimaryKey, O.AutoInc)

  def player1Name= column[Char]("player1Name")

  def player2Name= column[Char]("player2Name")

  def player3Name= column[Char]("player3Name")

  def player4Name= column[Char]("player4Name")

  def player5Name= column[Char]("player5Name")

  def * = (namesId, player1Name, player2Name, player3Name, player4Name, player5Name)
}
