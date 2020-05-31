package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.model.fileIOComponent.IDominionFileIO
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.{Roundmanager, RoundmanagerStatus}
import net.codingwell.scalaguice.ScalaModule
import de.htwg.sa.dominion.model.fileIOComponent._

class DominionModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[IRoundmanager].toInstance(Roundmanager(Nil, Nil, 0, 1, Nil, 0, gameEnd = false, Nil,
      RoundmanagerStatus.PLAY_CARD_PHASE, 0, Nil))
    bind[IDominionFileIO].to[JSONImpl.FileIO]
  }
}
