package de.htwg.sa.dominion.model.playerDatabaseComponent.slickImpl

import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase

import scala.util.Try

class PlayerMsSqlDAO extends IPlayerDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
