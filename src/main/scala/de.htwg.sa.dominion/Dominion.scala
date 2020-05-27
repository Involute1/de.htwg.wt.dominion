package de.htwg.sa.dominion

import com.google.inject.{Guice, Injector}
import de.htwg.sa.dominion.aview.{HttpServer, TUI}
import de.htwg.sa.dominion.aview.gui.SwingGui
import de.htwg.sa.dominion.controller.IController
import de.htwg.sa.dominion.controller.maincontroller.Controller

object Dominion {

  val injector: Injector = Guice.createInjector(new DominionModule)
  val controller: IController = injector.getInstance(classOf[Controller])
  val tui = new TUI(controller)
  val gui = new SwingGui(controller)
  val httpServer: HttpServer = new HttpServer(controller)
  val introString: String = "Welcome to Dominion! \n Press 'q' to exit and any other key to start "
  //controller.setControllerMessage(introString)
  //controller.notifyObservers

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