package de.htwg.wt.dominion.aview.gui

import de.htwg.wt.dominion.controller.IController
import de.htwg.wt.dominion.util.Observer

import scala.swing.{Action, Frame, Menu, MenuBar, MenuItem, Panel}

class SwingGui(controller: IController) extends Frame with Observer {

  controller.add(this)
  title = "Dominion"

  contents = new StartPanel(controller)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Save") {
        controller.save()
      })
      contents += new MenuItem(Action("Load") {
        controller.load()
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  visible = true
  centerOnScreen()
  resizable = true
  pack()

  override def update(): Boolean = {
    contents = SwingGui.getPanel(controller)
    repaint()
    true
  }
}

object SwingGui {
  def getPanel(controller: IController): Panel = {
    controller.getControllerStateAsString match {
      case "PreInitGameState" => new StartPanel(controller)
      case "PreStetupState" => new PlayerInitPanel(controller)
      case "PlayerSetupState" => new NameInitPanel(controller)
      case "ActionState" | "BuyState" => new PlayingPanel(controller)
      case "GameOverState" => new GameOverPanel(controller)
    }
  }
}
