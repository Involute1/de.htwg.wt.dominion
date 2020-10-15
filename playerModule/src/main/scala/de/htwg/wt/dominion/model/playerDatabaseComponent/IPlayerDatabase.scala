package de.htwg.wt.dominion.model.playerDatabaseComponent

import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player

import scala.util.Try

trait IPlayerDatabase {

  def create: Boolean

  def read(): List[Player]

  def update(playerList: List[Player]): Boolean

  def delete:Boolean

}
