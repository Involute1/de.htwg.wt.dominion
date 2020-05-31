package de.htwg.sa.dominion.model.fileIOComponent

import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import scala.util.Try

trait IDominionFileIO {

  def load(modelInterface: IRoundmanager): Try[(String, IRoundmanager)]

  def save(controllerState: String, modelInterface: IRoundmanager): Try[Boolean]

}
