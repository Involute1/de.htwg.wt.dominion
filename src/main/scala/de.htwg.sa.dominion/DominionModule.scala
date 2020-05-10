package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.ControllerInterface
import de.htwg.sa.dominion.controller.maincontroller.Controller
import de.htwg.sa.dominion.model.cardcomponent.Card
import de.htwg.sa.dominion.model.{CardInterface, PlayerInterface, RoundmanagerInterface}
import de.htwg.sa.dominion.model.playercomponent.Player
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager
import net.codingwell.scalaguice.ScalaModule

class DominionModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[PlayerInterface].to[Player]
    bind[CardInterface].to[Card]
    bind[ControllerInterface].to[Controller]
    bind[RoundmanagerInterface].to[Roundmanager]
    bind[RoundmanagerInterface].toInstance(Roundmanager(Nil, Nil, 0, 0, Nil, 0, gameEnd = false, Nil))
    //bind[Player].toInstance(Player())
  }

}
