package de.htwg.sa.dominion.model.fileIoComponent.XMLImpl

import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.ICardFileIO

import scala.util.Try

class FileIO extends ICardFileIO {

  override def load(card: ICard, path: String): Try[ICard] = ???

  override def save(card: ICard, path: String): Try[Boolean] = ???
}
