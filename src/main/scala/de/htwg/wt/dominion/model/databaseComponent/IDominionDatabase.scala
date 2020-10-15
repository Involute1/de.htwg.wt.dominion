package de.htwg.wt.dominion.model.databaseComponent

import de.htwg.wt.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.wt.dominion.model.roundmanagerComponent.roundmanagerBaseIml.Roundmanager

trait IDominionDatabase {

  def create: Boolean

  def read(): (String, Roundmanager)

  def update(controllerState: String, roundmanager: IRoundmanager): Boolean

  def delete: Boolean

}
