package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.controller.maincontroller.CardController
import de.htwg.sa.dominion.model.cardFileIoComponent.ICardFileIO
import de.htwg.sa.dominion.model.cardFileIoComponent.JSONImpl.FileIO
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.{Card, Cardtype}
import net.codingwell.scalaguice.ScalaModule

class CardModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ICard].toInstance(Card("", "", Cardtype.KINGDOM, 0, 0, 0, 0, 0, 0, 0))
    bind[ICardController].to[CardController]
    bind[ICardFileIO].to[FileIO]
  }
}
