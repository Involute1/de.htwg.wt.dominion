package de.htwg.sa.dominion.model.databaseComponent

import scala.util.Try

trait IDominionDatabase {

  def create: Try[Boolean]

  def read(): Unit

  def update: Try[Boolean]

  def delete: Try[Boolean]

}
