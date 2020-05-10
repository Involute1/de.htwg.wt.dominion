package de.htwg.sa.dominion.model

trait PlayerInterface {

  def constructPlayerNameString(): String

  def constructPlayerDeckString(): String

  def constructPlayerStackerString(): String

  def constructPlayerHandString(): String

}
