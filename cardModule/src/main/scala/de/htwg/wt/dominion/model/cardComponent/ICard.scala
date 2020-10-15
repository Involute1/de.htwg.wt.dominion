package de.htwg.wt.dominion.model.cardComponent

import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card
import play.api.libs.json.JsValue

import scala.xml.Elem

trait ICard {

  def constructCardNameString(): String

  def constructCardInformationString: String

  def toJson: JsValue

  def fromJson(jsValue: JsValue): ICard

  def toXml: Elem

  def fromXML(node: scala.xml.NodeSeq): ICard

  def listFromXml(node: scala.xml.NodeSeq): List[ICard]
}
