package de.htwg.wt.dominion

import com.google.inject.AbstractModule
import de.htwg.wt.dominion.controller.IPlayerController
import de.htwg.wt.dominion.controller.maincontroller.PlayerController
import de.htwg.wt.dominion.model.playerComponent.IPlayer
import de.htwg.wt.dominion.model.playerComponent.playerBaseImpl.Player
import de.htwg.wt.dominion.model.playerDatabaseComponent.IPlayerDatabase
import de.htwg.wt.dominion.model.playerDatabaseComponent.mongoImpl.PlayerMongoDbDAO
import de.htwg.wt.dominion.model.playerDatabaseComponent.slickImpl.PlayerMsSqlDAO
import de.htwg.wt.dominion.model.playerFileIoComponent.IPlayerFileIO
import de.htwg.wt.dominion.model.playerFileIoComponent.JSONImpl.FileIO
import net.codingwell.scalaguice.ScalaModule

class PlayerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IPlayer].toInstance(Player("", 0, Nil, Nil, Nil, 1, 1, 0))
    bind[IPlayerController].to[PlayerController]
    bind[IPlayerFileIO].to[FileIO]
    bind[IPlayerDatabase].to[PlayerMongoDbDAO]
  }
}
