package de.htwg.wt.dominion

import com.google.inject.{Guice, Injector}
import de.htwg.wt.dominion.aview.CardHttpServer
import de.htwg.wt.dominion.controller.maincontroller.CardController
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card

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
