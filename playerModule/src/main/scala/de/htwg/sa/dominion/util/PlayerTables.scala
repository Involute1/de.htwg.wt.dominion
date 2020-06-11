package de.htwg.sa.dominion.util

import slick.lifted.ProvenShape
import slick.jdbc.MySQLProfile.api._

object PlayerTables {

  class PlayerTable(tag: Tag) extends Table[(Int, Option[String], Option[Int], Option[Int], Option[Int], Option[Int])](tag, "PLAYER") {
    def id: Rep[Int] = column[Int]("PLAYER_ID", O.PrimaryKey, O.AutoInc)

    def name: Rep[Option[String]] = column[Option[String]]("NAME", O.SqlType("NVARCHAR(20)"))

    def value: Rep[Option[Int]] = column[Option[Int]]("VALUE")

    def actions: Rep[Option[Int]] = column[Option[Int]]("ACTIONS")

    def buys: Rep[Option[Int]] = column[Option[Int]]("BUYS")

    def money: Rep[Option[Int]] = column[Option[Int]]("MONEY")

    def * : ProvenShape[(Int, Option[String], Option[Int], Option[Int], Option[Int], Option[Int])] = (id, name, value, actions, buys, money)
  }

}
