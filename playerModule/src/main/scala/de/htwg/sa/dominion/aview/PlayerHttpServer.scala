package de.htwg.sa.dominion.aview

import de.htwg.sa.dominion.controller.IPlayerController
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.PlayerMain
import de.htwg.sa.dominion.controller.util.{UpdatedPlayerActions, UpdatedPlayerBuys}
import de.htwg.sa.dominion.util.UpdatedActionsContainer
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContextExecutor, Future}

class PlayerHttpServer(controller: IPlayerController) {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = concat(
    get {
      path("player") {
        toHtml("<h1>This is the Player Module Server of Dominion</h1>")
      }
    },
    get {
      path("player" / "exit") {
        PlayerMain.shutdown = true
        toHtml("<h3>Shutting down PlayerModule Webserver</h3>")
      }
    },
    get {
      path("player" / "save") {
        controller.save()
        complete("")
      }
    },
    get {
      path("player" / "load") {
        controller.load()
        complete("")
      }
    },
    get {
      path("player" / "constructPlayerNameString") {
        complete(controller.constructPlayerNameString())
      }
    },
    get {
      path("player" / "constructPlayerDeck") {
        complete(controller.constructPlayerDeckString())
      }
    },
    get {
      path("player" / "constructPlayerStackerString") {
        complete(controller.constructPlayerStackerString())
      }
    },
    get {
      path("player" / "constructPlayerHandString") {
        complete(controller.constructPlayerHandString())
      }
    },
    post {
      path("player" / "updateActions") {
        decodeRequest {
          entity(as[String]) {string => {
            complete(Json.toJson(UpdatedActionsContainer(controller.updateActions(string.toInt))).toString())
          }
          }
        }
      }
    },
    post {
      path("player" / "updateMoney") {
        decodeRequest {
          entity(as[String]) {string => {
            val updatedController = Json.fromJson(Json.parse(string))(UpdatedPlayerBuys.containerReads).get
            complete(Json.toJson(controller.updateBuys(updatedController.buys)).toString)
          }
          }
        }
      }
    },
    get{
      path("player" / "removeHandCardAddToStacker") {
        // TODO
        //controller.removeHandCardAddToStacker()
        complete("")
      }
    },
    get{
      path("player" / "updateMoney") {
        // TODO
        //controller.updateMoney()
        complete("")
      }
    },
    get{
      path("player" / "updateBuys") {
        // TODO
        //controller.updateBuys()
        complete("")
      }
    },
    get{
      path("player" / "CheckForFirstSilver") {
        controller.checkForFirstSilver()
        complete("")
      }
    },
    get{
      path("player" / "calculatePlayerMoneyForBuy") {
        // TODO
        //controller.calculatePlayerMoneyForBuy()
        complete("")
      }
    },
    get{
      path("player" / "discard") {
        // TODO
        //controller.discard()
        complete("")
      }
    },
    get{
      path("player" / "checkForTreasure") {
        controller.checkForTreasure()
        complete("")
      }
    },
    get{
      path("player" / "trashHandCard") {
        // TODO
        //controller.trashHandCard()
        complete("")
      }
    },
    get{
      path("player" / "constructCellarTrashstring") {
        complete(controller.constructCellarTrashString())
      }
    },
    get{
      path("player" / "removeCompleteHand") {
        // TODO
        //controller.removeCompleteHand()
        complete("")
      }
    },
    get{
      path("player" / "moveAllCardsToDeckForScore") {
        controller.moveAllCardsToDeckForScore()
        complete("")
      }
    },
    get{
      path("player" / "calculateScore") {
        complete(controller.calculateScore.toString())
      }
    }
  )

  println("PlayerModule Server online at http://localhost:8081/")

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
