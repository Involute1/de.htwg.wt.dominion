package de.htwg.wt.dominion.aview

import de.htwg.wt.dominion.controller.IController
import de.htwg.wt.dominion.util.Observer

class TUI(controller: IController) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "s" => controller.save()
      case "l" => controller.load()
      case _ => controller.eval(input)
    }
  }

  override def update(): Boolean = {
    println(controller.getControllerMessage)
    true
  }
}
