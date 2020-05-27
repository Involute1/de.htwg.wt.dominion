package de.htwg.sa.dominion.aview

import de.htwg.sa.dominion.controller.IPlayerController
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.controller.util.UpdatedPlayerContainer
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContextExecutor, Future}

class PlayerHttpServer(controller: IPlayerController) {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = concat(
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
            val updatedController = Json.fromJson(Json.parse(string))(UpdatedPlayerContainer.constainerReads).get
            controller.updateActions(updatedController.buys)
            complete("")
          }
          }
        }
      }
    },
    get{
      path("player" / "updateHand") {
        controller.updateHand()
        complete("")
      }
    },
    get{
      path("player" / "removeHandCardAddToStacker") {
        controller.removeHandCardAddToStacker()
        complete("")
      }
    },
    get{
      path("player" / "updateMoney") {
        controller.updateMoney()
        complete("")
      }
    },
    get{
      path("player" / "updateBuys") {
        controller.updateBuys()
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
        controller.calculatePlayerMoneyForBuy()
        complete("")
      }
    },
    get{
      path("player" / "discard") {
        controller.discard()
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
        controller.trashHandCard()
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
        controller.removeCompleteHand()
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

}
