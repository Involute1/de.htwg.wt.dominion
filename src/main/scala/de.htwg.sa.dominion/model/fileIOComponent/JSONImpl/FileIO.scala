package de.htwg.sa.dominion.model.fileIOComponent.JSONImpl

import java.io._
import de.htwg.sa.dominion.model.fileIOComponent.IDominionFileIO
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import play.api.libs.json._

import scala.io.Source
import scala.util.Try

class FileIO extends IDominionFileIO {

  override def load(modelInterface: IRoundmanager): Try[(String, IRoundmanager)] = {
    Try {
      val source = Source.fromFile("roundmanager.json")
      val sourceString = source.getLines.mkString
      source.close()
      val json = Json.parse(sourceString)

      val controllerStateString = (json \ "controllerState").get.as[String]
      val roundManager = modelInterface.fromJson((json \ "RoundManager").get)
      (controllerStateString, roundManager)
    }
  }

  override def save(controllerState: String, modelInterface: IRoundmanager): Try[Boolean] = {
    val savedGame = Json.obj (
      "controllerState" -> controllerState,
      "RoundManager" -> modelInterface.toJson
    )

    Try {
      val pw = new PrintWriter(new File("roundmanager.json"))
      pw.write(Json.prettyPrint(savedGame))
      pw.close()
      true
    }
  }
}