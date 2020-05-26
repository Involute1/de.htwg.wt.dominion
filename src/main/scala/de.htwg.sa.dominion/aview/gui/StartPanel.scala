package de.htwg.sa.dominion.aview.gui

import de.htwg.sa.dominion.controller.IController
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, FlowPanel, Label, Orientation}

class StartPanel(controller: IController) extends BoxPanel(Orientation.Vertical) {

  val localPlayButton: Button = new Button("Local")

  val quitButton: Button = new Button("Quit")

  contents += new FlowPanel() {
    contents += new Label {
      private val tmp = new ImageIcon("src/main/resources/dominion.png").getImage
      private val resize = tmp.getScaledInstance(2000, 800, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
    }
  }

  contents += new FlowPanel() {
    contents += localPlayButton
    contents += quitButton
  }

  listenTo(localPlayButton)
  listenTo(quitButton)

  reactions += {
    case ButtonClicked(`localPlayButton`) => controller.eval("n")
    case ButtonClicked(`quitButton`) => System.exit(0)
  }

}
