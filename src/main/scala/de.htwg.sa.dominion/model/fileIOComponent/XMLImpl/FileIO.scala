package de.htwg.sa.dominion.model.fileIOComponent.XMLImpl

import de.htwg.sa.dominion.model.ModelInterface
import de.htwg.sa.dominion.model.fileIOComponent.IFileIO
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

import scala.xml.Elem

class FileIO extends IFileIO {

  override def load(modelInterface: ModelInterface): (String, Roundmanager) = {
    val saveState = scala.xml.XML.loadFile("roundmanager.xml")
    val controllerStateString = (saveState \ "state").text.trim
    val state = controllerStateString
    val roundManager = modelInterface.fromXML((saveState \ "RoundManager").head)
    (state, roundManager)
  }

  override def save(controllerState: String, modelInterface: ModelInterface): Unit = {
    def gameToXml: Elem = {
      <Game>
        <state>
          {controllerState}
        </state>{modelInterface.toXML}
      </Game>
    }
    import java.io._
    val pw = new PrintWriter(new File("roundmanager.xml"))
    pw.write(gameToXml.toString())
    pw.close()
  }
}