package de.htwg.wt.dominion.model.cardFileIoComponent.XMLImpl

import de.htwg.wt.dominion.model.cardComponent.ICard
import de.htwg.wt.dominion.model.cardFileIoComponent.ICardFileIO

import scala.util.Try
import scala.xml.{Elem, XML}

class FileIO extends ICardFileIO {

  override def load(card: ICard, path: String): Try[ICard] = {
    Try {
      val source = XML.loadFile(path + ".xml")
      card.fromXML((source \ "Card").head)
    }
  }

  override def save(card: ICard, path: String): Try[Boolean] = {
    import java.io._
    Try {
      val pw = new PrintWriter(new File(path + ".xml"))
      pw.write(card.toXml.toString())
      pw.close()
      true
    }
  }
}
