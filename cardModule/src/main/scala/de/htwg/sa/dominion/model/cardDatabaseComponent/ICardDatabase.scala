package de.htwg.sa.dominion.model.cardDatabaseComponent

import scala.util.Try

trait ICardDatabase {
  def create: Try[Boolean]

  def read(): Unit

  def update: Try[Boolean]

  def delete: Try[Boolean]
}
