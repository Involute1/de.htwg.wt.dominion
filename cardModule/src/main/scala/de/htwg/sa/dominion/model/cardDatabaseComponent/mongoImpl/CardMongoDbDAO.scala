package de.htwg.sa.dominion.model.cardDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.cardDatabaseComponent.ICardDatabase

import scala.util.Try

class CardMongoDbDAO extends ICardDatabase {
  override def create: Try[Boolean] = ???

  override def read(): Unit = ???

  override def update: Try[Boolean] = ???

  override def delete: Try[Boolean] = ???
}
