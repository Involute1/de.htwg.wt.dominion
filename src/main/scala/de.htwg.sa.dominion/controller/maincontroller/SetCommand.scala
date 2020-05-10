package de.htwg.sa.dominion.controller.maincontroller

import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager
import de.htwg.sa.dominion.util.Command

class SetCommand(controller: Controller) extends Command {

  var memory: (Roundmanager, ControllerState) = (controller.roundManager.copy())

  override def doStep(): Unit = memory = (controller.roundManager.copy(), controller.controllerState)

  override def undoStep(): Unit = {
    val newMemory = (controller.roundManager.copy(), controller.controllerState)
    controller.roundManager = memory._1
    controller.controllerState = memory._2
    memory = newMemory
  }

  override def redoStep(): Unit = {
    val newMemory = (controller.roundManager.copy(), controller.controllerState)
    controller.roundManager = memory._1
    controller.controllerState = memory._2
    memory = newMemory
  }
}
