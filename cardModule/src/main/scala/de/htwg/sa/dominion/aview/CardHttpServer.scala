package de.htwg.sa.dominion.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.controller.ICardController

import scala.concurrent.{ExecutionContextExecutor, Future}

class CardHttpServer(controller: ICardController) {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = concat(
    get {
      path("card" / "save") {
        controller.save()
        complete("")
      }
    },
    get {
      path("card" / "load") {
        controller.load()
        complete("")
      }
    },
    get {
      path("card" / "cardname") {
        controller.constructCardNameString()
        complete("")
      }
    },
    get {
      path("card" / "cardinfo") {
        controller.constructCardInfoString()
        complete("")
      }
    }
  )

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 54251)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
