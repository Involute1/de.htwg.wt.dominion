package de.htwg.sa.dominion.aview

import de.htwg.sa.dominion.controller.IPlayerController
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.PlayerMain
import de.htwg.sa.dominion.controller.util.{UpdatedPlayerActions, UpdatedPlayerBuys}
import de.htwg.sa.dominion.util.{IntListContainer, PlayerHandStringContainer, UpdatedActionsContainer}
import play.api.libs.json.Json
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player

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
            controller.save()
            complete("")
          }
        } ~
        get {
          path("player" / "load") {
            controller.load()
            complete("")
          }
        } ~
        get {
          path("player" / "constructPlayerNameString") {
            complete(controller.constructPlayerNameString())
          }
        } ~
        get {
          path("player" / "constructPlayerDeck") {
            complete(controller.constructPlayerDeckString())
          }
        } ~
        get {
          path("player" / "constructPlayerStackerString") {
            complete(controller.constructPlayerStackerString())
          }
        } ~
        get {
          path("player" / "constructPlayerHandString") {

            //complete(controller.constructPlayerHandString())
            complete("")
          }
        }
    } ~
      post {
        path("player" / "updateActions") {
          decodeRequest {
            entity(as[String]) { string => {
              complete(Json.toJson(UpdatedActionsContainer(controller.updateActions(string.toInt))).toString())
            }
            }
          }
        }
      } ~
      post {
        path("player" / "updateMoney") {
          decodeRequest {
            entity(as[String]) { string => {
              complete(Json.toJson(UpdatedActionsContainer(controller.updateMoney(string.toInt))).toString())
            }
            }
          }
        }
      } ~
      post {
        path("player" / "updateBuys") {
          decodeRequest {
            entity(as[String]) { string => {
              complete(Json.toJson(UpdatedActionsContainer(controller.updateBuys(string.toInt))).toString())
            }
            }
          }
        }
      } ~
      post {
        path("player" / "removeHandCardAddToStacker") {
          decodeRequest {
            entity(as[String]) { string => {
              complete(Json.toJson(UpdatedActionsContainer(controller.removeHandCardAddToStacker(string.toInt))).toString())
            }
            }
          }
        }
      } ~
      post {
        path("player" / "trashHandCard") {
          decodeRequest {
            entity(as[String]) { string => {
              complete(Json.toJson(UpdatedActionsContainer(controller.trashHandCard(string.toInt))).toString())
            }
            }
          }
        }
      } ~
      get {
        path("player" / "CheckForFirstSilver") {
          controller.checkForFirstSilver()
          complete("")
        }
      } ~
      get {
        path("player" / "calculatePlayerMoneyForBuy") {
          controller.calculatePlayerMoneyForBuy
          complete("")
        }
      } ~
      post {
        path("player" / "discard") {
          decodeRequest {
            entity(as[String]) { string => {
              val container = Json.fromJson(Json.parse(string))(IntListContainer.containerReads).get
              controller.discard(container.list)
              complete("")
            }
            }
          }
        }
      } ~
      get {
        path("player" / "checkForTreasure") {
          controller.checkForTreasure()
          complete("")
        }
      } ~
      get {
        path("player" / "constructCellarTrashstring") {
          complete(controller.constructCellarTrashString())
        }
      } ~
      post {
        path("player" / "removeCompleteHand") {
          decodeRequest {
            entity(as[String]) { string => {
              val playerContainer = Json.fromJson(Json.parse(string))(UpdatedActionsContainer.containerReads).get
              complete(Json.toJson(UpdatedActionsContainer(controller.removeCompleteHand(playerContainer, string.toInt))).toString())
            }
            }
          }
        }
      } ~
      get {
        path("player" / "moveAllCardsToDeckForScore") {
          controller.moveAllCardsToDeckForScore()
          complete("")
        }
      } ~
      get {
        path("player" / "calculateScore") {
          complete(controller.calculateScore.toString())
        }
      }
  }

  println("PlayerModule Server online at http://localhost:8081/player")

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8081)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def toHtml(s: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s))
  }
}
