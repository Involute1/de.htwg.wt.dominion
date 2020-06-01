package de.htwg.sa.dominion.model.cardFileIoComponent.JSONImpl

import java.io.{File, PrintWriter}

import de.htwg.sa.dominion.model.cardFileIoComponent.ICardFileIO
import de.htwg.sa.dominion.model.cardComponent.ICard
import play.api.libs.json.Json

import scala.io.Source
import scala.util.Try

class FileIO extends ICardFileIO {
  override def load(card: ICard, path: String): Try[ICard] = {
    Try {
      val source = Source.fromFile(path + ".json")
      val string = source.getLines().mkString
      source.close
      val json = Json.parse(string)
      card.fromJson(json)
    }
  }

  override def save(card: ICard, path: String): Try[Boolean] = {
    Try {
      val printWriter = new PrintWriter(new File(path + ".json"))
      printWriter.write(Json.prettyPrint(card.toJson))
      printWriter.close()
      true
    }
  }
}
