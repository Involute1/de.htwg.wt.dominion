package de.htwg.sa.dominion.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card

import scala.concurrent.{ExecutionContextExecutor, Future}

class CardHttpServer(controller: ICardController) extends PlayJsonSupport {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = concat(
    get {
      path("card" / "savePlayingDecks") {
        entity(as[List[List[Card]]]) { params => {
          controller.save(Option(params), None, None, None, None)
          complete("")
        }}
      }
    },
    get {
      path("card" / "save") {
        entity(as[(List[Card], List[Card], List[Card], Int)]) { params => {
          controller.save(None, Option(params._1), Option(params._2), Option(params._3), Option(params._4))
          complete("")
        }}
      }
    },
    get {
      path("card" / "loadPlayingDecks") {
        complete(controller.load(None))
      }
    },
    get {
      path("card" / "load") {
        entity(as[Int]) { params => {
          complete(controller.load(Option(params)))
        }}
      }
    },
    get {
      path("card" / "cardname") {
        complete(controller.constructCardNameString())
      }
    },
    get {
      path("card" / "cardinfo") {
        complete(controller.constructCardInfoString())
      }
    }
  )

  println("CardModule Server online at http://localhost:8082/card")

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "0.0.0.0", 8082)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def toHtml(s: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s))
  }
}
