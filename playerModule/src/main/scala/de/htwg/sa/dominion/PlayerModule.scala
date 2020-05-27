package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.IPlayerController
import de.htwg.sa.dominion.controller.maincontroller.PlayerController
import de.htwg.sa.dominion.model.fileIoComponent.IPlayerFileIO
import de.htwg.sa.dominion.model.fileIoComponent._
import de.htwg.sa.dominion.model.playerComponent.IPlayer
import de.htwg.sa.dominion.model.playerComponent.playerBaseImpl.Player
import net.codingwell.scalaguice.ScalaModule

class PlayerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayer].toInstance(Player("", 0, Nil, Nil, Nil, 1, 1, 0))
    bind[IPlayerController].to[PlayerController]
    //bind[IPlayerFileIO].to[JSONImpl.FileIO]
  }
}
