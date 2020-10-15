package de.htwg.wt.dominion.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.wt.dominion.PlayerMain
import de.htwg.wt.dominion.controller.IPlayerController
import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player

import scala.concurrent.{ExecutionContextExecutor, Future}

case class PlayerHttpServer(controller: IPlayerController) extends PlayJsonSupport {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = {
    get {
      path("player") {
        toHtml("<h1>This is the Player Module Server of Dominion</h1>")
      } ~
        get {
          path("player" / "exit") {
            PlayerMain.shutdown = true
            toHtml("<h3>Shutting down PlayerModule Webserver</h3>")
          }
        } ~
        get {
          path("player" / "save") {
            entity(as[List[Player]]) { params => {
              controller.save(params)
              complete("")
            }}
          }
        } ~
        get {
          path("player" / "load") {
            complete(controller.load())
          }
        } ~
        get {
          path("player" / "constructPlayerNameString") {
            entity(as[Player]) { params => {
              complete(controller.constructPlayerNameString(params))
            }}
          }
        } ~
        get {
          path("player" / "constructPlayerDeckString") {
            entity(as[Player]) { params => {
              complete(controller.constructPlayerDeckString(params))
            }}
          }
        } ~
        get {
          path("player" / "constructPlayerStackerString") {
            entity(as[Player]) { params => {
              complete(controller.constructPlayerStackerString(params))
            }}
          }
        } ~
        get {
          path("player" / "constructPlayerHandString") {
            entity(as[Player]) { params => {
              println("reached")
              complete(controller.constructPlayerHandString(params))
            }}
          }
        } ~
        get {
          path("player" / "updateActions") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.updateActions(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "updateHand") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.updateHand(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "removeHandCardAddToStacker") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.removeHandCardAddToStacker(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "updateMoney") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.updateMoney(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "updateBuys") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.updateBuys(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "checkForFirstSilver") {
            entity(as[Player]) { params => {
              complete(controller.checkForFirstSilver(params))
            }}
          }
        } ~
        get {
          path("player" / "calculatePlayerMoneyForBuy") {
            entity(as[Player]) { params => {
              complete(controller.calculatePlayerMoneyForBuy(params))
            }}
          }
        } ~
        get {
          path("player" / "discard") {
            entity(as[(List[Int], Player)]) { params => {
              complete(controller.discard(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "checkForTreasure") {
            entity(as[Player]) { params => {
              complete(controller.checkForTreasure(params))
            }}
          }
        } ~
        get {
          path("player" / "trashHandCard") {
            entity(as[(Int, Player)]) { params => {
              complete(controller.trashHandCard(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "constructCellarTrashString") {
            entity(as[Player]) { params => {
              println("reached")
              complete(controller.constructCellarTrashString(params))
            }}
          }
        } ~
        get {
          path("player" / "removeCompleteHand") {
            entity(as[(Player, Int)]) { params => {
              println(params._1)
              complete(controller.removeCompleteHand(params._1, params._2))
            }}
          }
        } ~
        get {
          path("player" / "moveAllCardsToDeckForScore") {
            entity(as[Player]) { params => {
              complete(controller.moveAllCardsToDeckForScore(params))
            }}
          }
        } ~
        get {
          path("player" / "calculateScore") {
            entity(as[Player]) { params => {
              complete(controller.calculateScore(params))
            }}
          }
        }
      }
  }

  println("PlayerModule Server online at http://localhost:8081/player")

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "0.0.0.0", 8081)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def toHtml(s: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s))
  }
}
