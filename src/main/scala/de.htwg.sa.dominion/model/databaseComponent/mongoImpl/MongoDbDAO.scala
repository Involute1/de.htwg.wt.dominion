package de.htwg.sa.dominion.model.databaseComponent.mongoImpl

import de.htwg.sa.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

import scala.util.Try

class MongoDbDAO extends IDominionDatabase {
  override def create: Boolean = ???

  override def read(): (String, Roundmanager) = ???

  override def update(controllerState: String, roundmanager: IRoundmanager): Boolean = ???

  override def delete: Boolean = ???
}
