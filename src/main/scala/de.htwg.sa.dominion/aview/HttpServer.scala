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

  val route: Route = {
    get {
    path("dominion") {
      toHtml
    }~
      path("dominion" / "undo") {
        controller.undo()
        toHtml
      }~
      path("dominion" / "redo") {
        controller.redo()
        toHtml
      }~
      path("dominion" / "save") {
        controller.save()
        toHtml
      }~
      path("dominion" / "load") {
        controller.load()
        toHtml
      }~
      path("dominion" / Segment) {
        input =>
          controller.eval(input)
          toHtml
      }
  }~
    post {
      path("dominion") {
        decodeRequest {
          entity(as[String]) { input => {
            val strippedInput = input.replace("input=", "")
            if (strippedInput.equalsIgnoreCase("save")) {
              controller.save()
            } else if (strippedInput.equalsIgnoreCase("load")) {
              controller.load()
            } else {
              controller.eval(strippedInput)
            }
            toHtml
          }
          }
        }
      }
    }
  }

  println(s"Dominion Module Server online at http://localhost:8080/dominion")

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "0.0.0.0", 8080)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def toHtml: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Dominion</h1>" + controller.toHTML +
    """</p><form action="/dominion" method="post">
        | <input type="text" id="input" name="input">
        | <input type="submit" value="Submit">
        |</form> """.stripMargin))
  }


}
