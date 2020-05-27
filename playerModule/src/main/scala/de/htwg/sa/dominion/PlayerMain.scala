package de.htwg.sa.dominion

import com.google.inject.{Guice, Injector}
import de.htwg.sa.dominion.aview.PlayerHttpServer
import de.htwg.sa.dominion.controller.maincontroller.PlayerController

object PlayerMain {

  def main(args: Array[String]): Unit = {
    while(!shutdown) {
      Thread.sleep(100)
    }
    httpServer.shutdownWebServer()
  }
}
