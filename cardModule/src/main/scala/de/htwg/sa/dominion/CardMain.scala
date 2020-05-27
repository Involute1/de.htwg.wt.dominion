package de.htwg.sa.dominion

import com.google.inject.{Guice, Injector}
import de.htwg.sa.dominion.aview.CardHttpServer
import de.htwg.sa.dominion.controller.maincontroller.CardController
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

object CardMain {

  val injector: Injector = Guice.createInjector(new CardModule)
  val controller: CardController = injector.getInstance(classOf[CardController])
  val httpServer: CardHttpServer = new CardHttpServer(controller)

  @volatile var shutdown: Boolean = false

  def main(args: Array[String]): Unit = {
    while(!shutdown) {
      Thread.sleep(100)
    }
    httpServer.shutdownWebServer()
  }
}
