package de.htwg.sa.dominion.model.databaseComponent.slickImpl

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase

import scala.util.Try

class MsSqlDAO extends IDominionDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???

  def getDbConnection: Unit = {

    val db = Database.forConfig("mydb")
    db
  }
}
