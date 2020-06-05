package de.htwg.sa.dominion.model.playerDatabaseComponent

import scala.util.Try

trait IPlayerDatabase {

  def create: Try[Boolean]

  def read(): Unit

  def update: Try[Boolean]

  def delete: Try[Boolean]

}
