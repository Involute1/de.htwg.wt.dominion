package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.IPlayerController
import de.htwg.sa.dominion.model.fileIoComponent.IFileIO
import de.htwg.sa.dominion.model.playercomponent.IPlayer
import de.htwg.sa.dominion.model.fileIoComponent._
import de.htwg.sa.dominion.model.playercomponent.playerBaseImpl.Player
import net.codingwell.scalaguice.ScalaModule

class PlayerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayer].toInstance(Player())
    bind[IPlayerController].to(PlayerController)
    bind[IFileIO].to(JSONImpl.FileIO)
  }
}
