package de.htwg.sa.dominion.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.controller.ICardController

import scala.concurrent.{ExecutionContextExecutor, Future}

class CardHttpServer(controller: ICardController) {
  implicit val system: ActorSystem = ActorSystem("my-system")
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

  println("CardModule Server online at http://localhost:8082/card")

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8082)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def toHtml(s: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s))
  }
}
