package de.htwg.sa.dominion.model.playerDatabaseComponent.slickImpl

import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.sa.dominion.util.PlayerTables.PlayerTable
import slick.jdbc.SQLServerProfile
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._

import scala.util.Try

class PlayerMsSqlDAO extends IPlayerDatabase {

  val playerTable = TableQuery[PlayerTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  override def create: Try[Boolean] = {
    Try {
      val setup = DBIO.seq(playerTable.schema.createIfNotExists)
      db.run(setup)
      true
    }
  }

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
