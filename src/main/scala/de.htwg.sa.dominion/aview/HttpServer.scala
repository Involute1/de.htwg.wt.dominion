package de.htwg.sa.dominion.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.sa.dominion.controller.IController

import scala.concurrent.{ExecutionContext, Future}

class HttpServer(controller: IController) {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val route: Route = get {
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>HTWG Dominion</h1>"))
    }
    path("dominion") {
      stuffToHtml
    }
    path("dominion" / "undo") {
      controller.undo()
      stuffToHtml
    }
    path("dominion" / "redo") {
      controller.redo()
      stuffToHtml
    }
    path("dominion" / "save") {
      controller.save()
      stuffToHtml
    }
    path("dominion" / "load") {
      controller.load()
      stuffToHtml
    }
    path("dominion" / Segment) { command => {
      processInputLine(command)
      stuffToHtml
    }}
  }

  def stuffToHtml: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>HTWG Dominion</h1>"))
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8080)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "h" => controller.getHelpPage()
      case "s" => controller.save()
      case "l" => controller.load()
      case _ => controller.eval(input)
    }
  }
}
