package de.htwg.sa.dominion.model.cardComponent

import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import play.api.libs.json.JsValue

trait ICard {

  def constructCardNameString(): String

  def constructCardInformationString: String
}
