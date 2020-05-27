package de.htwg.sa.dominion.model.fileIoComponent.JSONImpl

import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.IFileIO

import scala.util.Try

class FileIO extends IFileIO {
  override def load(card: ICard, path: String): Try[ICard] = ???

  override def save(card: ICard, path: String): Try[Boolean] = ???
}
