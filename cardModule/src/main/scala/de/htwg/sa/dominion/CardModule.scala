package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.controller.maincontroller.CardController
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cardtype}
import de.htwg.sa.dominion.model.fileIoComponent.ICardFileIO
import de.htwg.sa.dominion.model.fileIoComponent._
import net.codingwell.scalaguice.ScalaModule

class CardModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ICard].toInstance(Card("", "", Cardtype.KINGDOM, 0, 0, 0, 0, 0, 0, 0))
    bind[ICardFileIO].to[JSONImpl.FileIO]
    //bind[ICardController].to[CardController]
  }
}
