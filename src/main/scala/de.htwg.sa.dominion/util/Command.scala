package de.htwg.sa.dominion.util

trait Command {

  def doStep():Unit

  def undoStep():Unit

  def redoStep():Unit

}
