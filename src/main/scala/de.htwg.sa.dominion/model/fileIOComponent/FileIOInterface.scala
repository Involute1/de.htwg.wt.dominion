package de.htwg.sa.dominion.model.fileIOComponent

import de.htwg.sa.dominion.model.ModelInterface
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

trait FileIOInterface {

  def load(modelInterface: ModelInterface): (String, Roundmanager)
  def save(controllerState: String ,modelInterface: ModelInterface): Unit

}
