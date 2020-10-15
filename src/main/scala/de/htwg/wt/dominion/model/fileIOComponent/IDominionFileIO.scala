package de.htwg.wt.dominion.model.fileIOComponent

import de.htwg.wt.dominion.model.roundmanagerComponent.IRoundmanager

import scala.util.Try

trait IDominionFileIO {

  def load(IRoundmanager: IRoundmanager): Try[(String, IRoundmanager)]

  def save(controllerState: String, IRoundmanager: IRoundmanager): Try[Boolean]

}
