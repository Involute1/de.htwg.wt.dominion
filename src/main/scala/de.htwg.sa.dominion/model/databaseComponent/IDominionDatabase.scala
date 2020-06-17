package de.htwg.sa.dominion.model.databaseComponent

import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

trait IDominionDatabase {

  def create: Boolean

  def read(): (String, Roundmanager)

  def update(controllerState: String, roundmanager: IRoundmanager): Boolean

  def delete: Boolean

}
