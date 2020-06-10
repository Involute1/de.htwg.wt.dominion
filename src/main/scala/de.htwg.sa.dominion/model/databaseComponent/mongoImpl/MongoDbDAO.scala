package de.htwg.sa.dominion.model.databaseComponent.mongoImpl

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase

import scala.util.Try

class MongoDbDAO extends IDominionDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
