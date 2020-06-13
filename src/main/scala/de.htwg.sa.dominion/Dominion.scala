package de.htwg.sa.dominion

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import com.google.inject.{Guice, Injector}
import de.htwg.sa.dominion.aview.{HttpServer, TUI}
import de.htwg.sa.dominion.aview.gui.SwingGui
import de.htwg.sa.dominion.controller.IController
import de.htwg.sa.dominion.controller.maincontroller.Controller

object Dominion {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val injector: Injector = Guice.createInjector(new DominionModule)
  val controller: IController = injector.getInstance(classOf[Controller])

  val tui = new TUI(controller)
  //val gui = new SwingGui(controller)
  val httpServer: HttpServer = new HttpServer(controller)

  val introString: String = "Welcome to Dominion! \n Press 'q' to exit and any other key to start "
  controller.setControllerMessage(introString)
  //PlayerMain.main(Array())
  CardMain.main(Array())
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.length > 0) input = args(0)
    if (!input.isEmpty) tui.processInputLine(input)
    else do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "q")
    httpServer.unbind()
    //Http().singleRequest(HttpRequest(uri = "http://localhost:8081/player/exit"))
    //Http().singleRequest(HttpRequest(uri = "http://localhost:8082/card/exit"))
  }
}