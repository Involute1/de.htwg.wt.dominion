package de.htwg.wt.dominion.aview

import de.htwg.wt.dominion.controller.{EvalEvent, IController}
import de.htwg.wt.dominion.util.Observer

import scala.swing.Reactor

class TUI(controller: IController) extends Reactor {

  listenTo(controller)

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

  reactions += {
    case event: EvalEvent => println(controller.getControllerMessage)
  }

//  override def update(): Boolean = {
//    println(controller.getControllerMessage)
//    true
//  }
}
