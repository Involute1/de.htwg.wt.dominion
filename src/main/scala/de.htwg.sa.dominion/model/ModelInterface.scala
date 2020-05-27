package de.htwg.sa.dominion.model

import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager
import play.api.libs.json.{JsLookupResult, JsObject, JsValue}

import scala.xml.Elem

trait ModelInterface {

  def toXML: Elem

  def fromXML(node: scala.xml.Node): Roundmanager

  def toJson: JsValue

  def fromJson(jsValue: JsValue): Roundmanager

}