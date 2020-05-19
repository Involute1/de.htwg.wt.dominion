package de.htwg.sa.dominion.aview.gui

import de.htwg.sa.dominion.controller.ControllerInterface

import scala.swing.{BoxPanel, Button, Orientation}

class StartPanel(controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) {

  val localPlayButton: Button = new Button("Local")

  val quitButton: Button = new Button("Quit")

}
