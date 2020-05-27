package de.htwg.sa.dominion.model.fileIOComponent.JSONImpl

import de.htwg.sa.dominion.model.ModelInterface
import de.htwg.sa.dominion.model.fileIOComponent.IDominionFileIO
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import play.api.libs.json._

import scala.io.Source

class FileIO extends IDominionFileIO {

  override def load(modelInterface: ModelInterface): (String, Roundmanager) = {
    val source = Source.fromFile("roundmanager.json")
    val sourceString = source.getLines.mkString
    source.close()
    val json = Json.parse(sourceString)

    val controllerStateString = (json \ "controllerState").get.as[String]
    val roundManager = modelInterface.fromJson((json \ "RoundManager").get)
    (controllerStateString, roundManager)
  }

  override def save(controllerState: String, modelInterface: ModelInterface): Unit = {
    val savedGame = Json.obj (
      "controllerState" -> controllerState,
      "RoundManager" -> modelInterface.toJson
    )

    import java.io._
    val pw = new PrintWriter(new File("roundmanager.json"))
    pw.write(Json.prettyPrint(savedGame))
    pw.close()
  }
}