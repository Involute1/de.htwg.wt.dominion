package de.htwg.sa.dominion.model.fileIoComponent

import de.htwg.sa.dominion.model.cardComponent.ICard

import scala.util.Try

trait ICardFileIO {

  def load(card: ICard, path: String): Try[ICard]

  def save(card: ICard, path: String): Try[Boolean]
}
