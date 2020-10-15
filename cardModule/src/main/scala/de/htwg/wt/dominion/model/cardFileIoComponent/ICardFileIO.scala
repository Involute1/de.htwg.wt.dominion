package de.htwg.wt.dominion.model.cardFileIoComponent

import de.htwg.wt.dominion.model.cardComponent.ICard

import scala.util.Try

trait ICardFileIO {

  def load(card: ICard, path: String): Try[ICard]

  def save(card: ICard, path: String): Try[Boolean]
}
