package de.htwg.sa.dominion.model.playerDatabaseComponent

import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player

import scala.util.Try

trait IPlayerDatabase {

  def create: Try[Boolean]

  def read(): Unit

  def update(playerList: List[Player]): Try[Boolean]

  def delete: Try[Boolean]

}
