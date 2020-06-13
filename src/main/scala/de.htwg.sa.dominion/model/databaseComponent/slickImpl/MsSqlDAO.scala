package de.htwg.sa.dominion.model.databaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.util.RoundManagerTables.{NameTable, RoundmanagerTable, ScoreTable}
import slick.jdbc.{JdbcProfile, SQLServerProfile}
import slick.lifted.TableQuery
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

class MsSqlDAO extends IDominionDatabase {

  val roundManagerTable: TableQuery[RoundmanagerTable] = TableQuery[RoundmanagerTable]
  val namesTable: TableQuery[NameTable] = TableQuery[NameTable]
  val scoreTable: TableQuery[ScoreTable] = TableQuery[ScoreTable]

  val db: SQLServerProfile.backend.Database = Database.forConfig("mymssqldb")

  override def create: Try[Boolean] = {
    Try {
      // for debugging
      //(roundManagerTable.schema ++ namesTable.schema ++ scoreTable.schema).createIfNotExists.statements.foreach(println)
      val setup = DBIO.seq((roundManagerTable.schema ++ namesTable.schema ++ scoreTable.schema).createIfNotExists)
      db.run(setup)
      true
    }
  }

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???

}
