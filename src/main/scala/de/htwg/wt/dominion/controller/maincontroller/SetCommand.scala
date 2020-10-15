package de.htwg.wt.dominion.controller.maincontroller

import de.htwg.wt.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.wt.dominion.util.Command

class SetCommand(controller: Controller) extends Command {

  var memory: (IRoundmanager, ControllerState) = (controller.roundmanager, controller.controllerState)

  override def doStep(): Unit = memory = (controller.roundmanager, controller.controllerState)

  override def undoStep(): Unit = {
    val newMemory = (controller.roundmanager, controller.controllerState)
    controller.roundmanager = memory._1
    controller.controllerState = memory._2
    memory = newMemory
  }

  override def redoStep(): Unit = {
    val newMemory = (controller.roundmanager, controller.controllerState)
    controller.roundmanager = memory._1
    controller.controllerState = memory._2
    memory = newMemory
  }
}
