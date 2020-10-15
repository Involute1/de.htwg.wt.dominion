package de.htwg.wt.dominion.model.playerFileIoComponent.XMLImpl

import de.htwg.wt.dominion.model.playerComponent.IPlayer
import de.htwg.wt.dominion.model.playerFileIoComponent.IPlayerFileIO

import scala.util.Try
import scala.xml.XML

class FileIO extends IPlayerFileIO {
  override def load(player: IPlayer, path: String): Try[IPlayer] = {
    Try {
      val source = XML.loadFile(path + ".xml")
      player.fromXml((source \ "Player").head)
    }
  }

  override def save(player: IPlayer, path: String): Try[Boolean] = {
    import java.io._
    Try {
      val pw = new PrintWriter(new File(path + ".xml"))
      pw.write(player.toXml.toString())
      pw.close()
      true
    }
  }
}
