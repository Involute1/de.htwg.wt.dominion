package de.htwg.sa.dominion.aview.gui

import de.htwg.sa.dominion.controller.IController
import javax.swing.ImageIcon

import scala.swing.event.{ButtonClicked, Key, KeyPressed}
import scala.swing.{BoxPanel, Button, Dimension, FlowPanel, Font, Label, Orientation, Swing, TextField}

class NameInitPanel(controller: IController) extends BoxPanel(Orientation.Vertical) {

  val currentPlayerturn: Int = controller.getNameListSize + 1
  val nameTextBox: TextField = new TextField() {
    listenTo(keys)
    reactions += {
      case KeyPressed(_, Key.Enter, _, _) => controller.eval(text)
    }
  }

  contents += new FlowPanel() {
    contents += new Label {
      private val temp = new ImageIcon("src/main/resources/dominion.png").getImage
      private val resize = temp.getScaledInstance(2000, 800, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
    }
  }

  contents += new FlowPanel() {
    contents += new Label("Player " + currentPlayerturn + ", please enter your name:")
  }

  val nextButton = new Button("\u2192")
  val prevButton = new Button("\u2190")

  listenTo(nextButton)
  listenTo(prevButton)

  contents += nameTextBox
  contents += Swing.RigidBox(new Dimension(0,20))


  contents += new BoxPanel(Orientation.Horizontal) {
    contents += prevButton
    contents += nextButton
  }

  reactions += {
    case ButtonClicked(`nextButton`) => controller.eval(nameTextBox.text)
    case ButtonClicked(`prevButton`) => controller.undo()
  }
}
