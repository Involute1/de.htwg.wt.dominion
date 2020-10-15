package de.htwg.wt.dominion.aview.gui

import de.htwg.wt.dominion.controller.IController

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, Font, Label, Orientation}

class GameOverPanel(controller: IController) extends BoxPanel(Orientation.Vertical) {
  val myFont = new Font("Charlemagne Std Bold", java.awt.Font.BOLD, 15)
  preferredSize = new Dimension(1800, 1200)

  val quitButton = new Button("Quit")
  listenTo(quitButton)

  val scorePanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    val labelList: List[Label] = for (playerScore <- controller.getScore) yield new Label {
      text = playerScore._1 + ", Points: " + playerScore._2
      font = myFont
    }
    labelList.foreach(x => contents += x)
  }

  val scorePanelWQuit: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += scorePanel
    contents += quitButton
  }

  contents += new BorderPanel {
    layout(scorePanelWQuit) = BorderPanel.Position.Center
  }

  reactions += {
    case ButtonClicked(`quitButton`) => System.exit(0)
  }
}
