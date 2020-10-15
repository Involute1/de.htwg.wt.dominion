package de.htwg.wt.dominion

import com.google.inject.AbstractModule
import de.htwg.wt.dominion.model.roundmanagerComponent.roundmanagerBaseIml.RoundmanagerStatus
import net.codingwell.scalaguice.ScalaModule
import de.htwg.wt.dominion.model.fileIOComponent._
import de.htwg.wt.dominion.model.databaseComponent.IDominionDatabase
import de.htwg.wt.dominion.model.databaseComponent.mongoImpl.MongoDbDAO
import de.htwg.wt.dominion.model.databaseComponent.slickImpl.MsSqlDAO
import de.htwg.wt.dominion.model.fileIOComponent.IDominionFileIO
import de.htwg.wt.dominion.model.fileIOComponent.JSONImpl.FileIO
import de.htwg.wt.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.wt.dominion.model.roundmanagerComponent.roundmanagerBaseIml.{Roundmanager, RoundmanagerStatus}

class DominionModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[IRoundmanager].toInstance(Roundmanager(Nil, Nil, 0, 1, Nil, 0, gameEnd = false, Nil,
      RoundmanagerStatus.PLAY_CARD_PHASE, 0, Nil))
    bind[IDominionFileIO].to[FileIO]
    bind[IDominionDatabase].to[MongoDbDAO]
  }
}
