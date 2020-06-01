package de.htwg.sa.dominion.model.playerFileIoComponent

import de.htwg.sa.dominion.model.playerComponent.IPlayer

import scala.util.Try

trait IPlayerFileIO {

  def load(player: IPlayer, path: String): Try[IPlayer]

  def save(player: IPlayer, path: String): Try[Boolean]
}
