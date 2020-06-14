package de.htwg.sa.dominion.model.databaseComponent

import de.htwg.sa.dominion.controller.maincontroller.ControllerState
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

import scala.util.Try

trait IDominionDatabase {

  def create: Try[Boolean]

  def read(): Roundmanager

  def update(controllerState: String, roundmanager: IRoundmanager): Try[Boolean]

  def delete: Try[Boolean]

}
