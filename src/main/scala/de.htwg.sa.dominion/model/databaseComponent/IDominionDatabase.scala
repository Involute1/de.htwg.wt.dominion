package de.htwg.sa.dominion.model.databaseComponent

import de.htwg.sa.dominion.controller.maincontroller.ControllerState
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

import scala.util.Try

trait IDominionDatabase {

  def create: Boolean

  def read(): (String, Roundmanager)

  def update(controllerState: String, roundmanager: IRoundmanager): Boolean

  def delete: Boolean

}
