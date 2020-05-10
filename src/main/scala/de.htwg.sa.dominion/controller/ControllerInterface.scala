package de.htwg.sa.dominion.controller
import de.htwg.sa.dominion.util.Observable

trait ControllerInterface extends Observable {

  def eval(input: String)

  def undo(): Unit

  def redo(): Unit

  def getControllerMessage: String

  def setControllerMessage(message: String): Unit

  def getHelpPage(): Unit
}
