package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase

import scala.util.Try

class PlayerMongoDbDAO extends IPlayerDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
