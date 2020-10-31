package de.htwg.wt.dominion

import com.google.inject.{Guice, Injector}
import de.htwg.wt.dominion.aview.PlayerHttpServer
import de.htwg.wt.dominion.controller.maincontroller.PlayerController

object PlayerMain {

  @volatile var shutdown: Boolean = false

  def main(args: Array[String]): Unit = {
    val injector: Injector = Guice.createInjector(new PlayerModule)
    val controller: PlayerController = injector.getInstance(classOf[PlayerController])
    val httpServer: PlayerHttpServer = new PlayerHttpServer(controller)

//    while(!shutdown) {
//      Thread.sleep(100)
//    }
//    httpServer.shutdownWebServer()
  }
}
