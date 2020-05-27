package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.IController
import de.htwg.sa.dominion.controller.maincontroller.Controller
import de.htwg.sa.dominion.model.playercomponent.IPlayer
import de.htwg.sa.dominion.model.roundmanagerComponent.IRoundmanager
import de.htwg.sa.dominion.model.roundmanagerComponent.roundmanagerBaseIml.{Roundmanager, RoundmanagerStatus}
import net.codingwell.scalaguice.ScalaModule

class DominionModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    //bind[PlayerInterface].to[Player]
    //bind[CardInterface].to[Card]
    //bind[ControllerInterface].to[Controller]
    //bind[RoundmanagerInterface].to[Roundmanager]
    bind[IRoundmanager].toInstance(Roundmanager(Nil, Nil, 0, 1, Nil, 0, gameEnd = false, Nil,
      RoundmanagerStatus.PLAY_CARD_PHASE, 0, Nil))
  }

}
