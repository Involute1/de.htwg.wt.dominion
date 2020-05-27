package de.htwg.sa.dominion

import com.google.inject.AbstractModule
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.cardComponent.cardBaseImpl.Card
import de.htwg.sa.dominion.model.fileIoComponent.IFileIO
import de.htwg.sa.dominion.model.fileIoComponent._
import net.codingwell.scalaguice.ScalaModule

class CardModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ICard].toInstance(Card())
    bind[IFileIO].to(JSONImpl.FileIO)
    bind[ICardController].to(CardController)
  }
}
