package de.htwg.sa.dominion.model.playerDatabaseComponent.mongoImpl

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.sa.dominion.model.playerDatabaseComponent.IPlayerDatabase

import scala.util.Try

class PlayerMongoDbDAO extends IPlayerDatabase {
  override def create: Boolean = ???

  override def read(): List[Player] = ???

  override def update(playerList: List[Player]): Boolean = ???

  override def delete: Boolean = ???
}
