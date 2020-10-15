package de.htwg.wt.dominion.aview.gui

import de.htwg.wt.dominion.controller.IController
import javax.swing.ImageIcon

import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, FlowPanel, Label, Orientation}

class PlayerInitPanel(controller: IController) extends BoxPanel(Orientation.Vertical) {
  val threePlayerButton: Button = new Button("3 Players")

  val fourPlayerButton: Button = new Button("4 Players")

  val fivePlayerButton: Button = new Button("5 Players")

  contents += new FlowPanel() {
    contents += new Label {
      private val temp = new ImageIcon("src/main/resources/dominion.png").getImage
      private val resize = temp.getScaledInstance(2000, 800, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
    }
  }

  contents += new FlowPanel() {
    contents += new Label("How many Players are you?")
  }

  contents += new FlowPanel() {
    contents += threePlayerButton
    contents += fourPlayerButton
    contents += fivePlayerButton
  }

  listenTo(threePlayerButton)
  listenTo(fourPlayerButton)
  listenTo(fivePlayerButton)

  reactions += {
    case ButtonClicked(`threePlayerButton`) => controller.eval("3")
    case ButtonClicked(`fourPlayerButton`) => controller.eval("4")
    case ButtonClicked(`fivePlayerButton`) => controller.eval("5")
  }
}
