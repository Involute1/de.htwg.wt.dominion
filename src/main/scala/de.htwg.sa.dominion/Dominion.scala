package de.htwg.sa.dominion

import de.htwg.sa.dominion.aview.TUI
import de.htwg.sa.dominion.controller.ControllerInterface
import de.htwg.sa.dominion.controller.maincontroller.Controller

object Dominion {

  val controller: ControllerInterface = new Controller()
  val tui = new TUI(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.length > 0) input = args(0)
    if (!input.isEmpty) tui.processInputLine(input)
    else do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }

}