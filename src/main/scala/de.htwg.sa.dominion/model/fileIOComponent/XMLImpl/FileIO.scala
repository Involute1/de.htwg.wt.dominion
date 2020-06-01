package de.htwg.sa.dominion.model.fileIOComponent.XMLImpl


import de.htwg.sa.dominion.model.fileIOComponent.IDominionFileIO
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager

import scala.util.Try
import scala.xml.Elem

class FileIO extends IDominionFileIO {

  override def load(IRoundmanager: IRoundmanager): Try[(String, IRoundmanager)] = {
    Try {
      val saveState = scala.xml.XML.loadFile("roundmanager.xml")
      val controllerStateString = (saveState \ "state").text.trim
      val state = controllerStateString
      val roundManager = IRoundmanager.fromXml((saveState \ "RoundManager").head)
      (state, roundManager)
    }
  }

  override def save(controllerState: String, IRoundmanager: IRoundmanager): Try[Boolean] = {
    def gameToXml: Elem = {
      <Game>
        <state>
          {controllerState}
        </state>{IRoundmanager.toXml}
      </Game>
    }
    Try {
      import java.io._
      val pw = new PrintWriter(new File("roundmanager.xml"))
      pw.write(gameToXml.toString())
      pw.close()
      true
    }
  }
}