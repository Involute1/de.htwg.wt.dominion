package de.htwg.sa.dominion.model.databaseComponent.slickImpl

import java.util.concurrent.TimeUnit

import de.htwg.sa.dominion.controller.maincontroller.ControllerState
import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
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

  override def create: Boolean = {
    try {
      val setup = DBIO.seq((roundManagerTable.schema ++ namesTable.schema ++ scoreTable.schema).createIfNotExists)
      db.run(setup)
      true
    } catch {
      case error: Error => println("Database error: ", error) false
    }
  }

  override def read(): (String, Roundmanager) = ???

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = {
    try {
      val currentRoundmanger: Roundmanager = roundmanager.getCurrentInstance
      val namesTuple = currentRoundmanger.names match {
        case List(a, b, c) => (0, Option(a), Option(b), Option(c), Option(null), Option(null))
        case List(a, b, c, d) => (0, Option(a), Option(b), Option(c), Option(d), Option(null))
        case List(a, b, c, d, e) => (0, Option(a), Option(b), Option(c), Option(d), Option(e))
      }
      val namesInsert = (namesTable returning namesTable.map(_.namesId) += (namesTuple))

      val scoreTuple = currentRoundmanger.score match {
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int)) => (0, Option(aInt), Option(bInt), Option(cInt), None, None)
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int), (dString, dInt: Int)) => (0, Option(aInt), Option(bInt), Option(cInt), Option(dInt), None)
        case List((aString, aInt: Int), (bString, bInt: Int), (cString, cInt: Int), (dString, dInt: Int), (eString, eInt: Int))
        => (0, Option(aInt), Option(bInt), Option(cInt), Option(dInt), Option(eInt))
        case _ => (0, None, None, None, None, None)
      }
      val scoreInsert = (scoreTable returning scoreTable.map(_.scoreId) += (scoreTuple))

      db.run(scoreTable.delete)
      db.run(namesTable.delete)
      val scoreId = Await.result(db.run(scoreInsert), Duration(1, TimeUnit.SECONDS))
      val namesId = Await.result(db.run(namesInsert), Duration(1, TimeUnit.SECONDS))

      val roundmanagerInsert = roundManagerTable.map(c => (c.numberOfPlayers, c.turn, c.emptyDeckCount, c.gameEnd,
        c.roundStatus, c.playerTurn, c.controllerSate, c.scoreIdFKey, c.namesIdFKey)) += (
        Option(currentRoundmanger.numberOfPlayers), Option(currentRoundmanger.turn), Option(currentRoundmanger.emptyDeckCount),
        Option(currentRoundmanger.gameEnd), Option(currentRoundmanger.roundStatus.toString), Option(currentRoundmanger.playerTurn),
        Option(controllerState), Option(scoreId), Option(namesId))

      db.run(roundManagerTable.delete)
      db.run(roundmanagerInsert)
      true
    } catch {
      case error: Error => println("Database error: ", error) false
    }
  }

  override def delete: Boolean = ???

}
