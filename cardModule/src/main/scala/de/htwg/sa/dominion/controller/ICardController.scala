package de.htwg.sa.dominion.controller

trait ICardController {

  def save(): Unit

  def load(): Unit

  def constructCardNameString(): String

  def constructCardInfoString(): String
}
