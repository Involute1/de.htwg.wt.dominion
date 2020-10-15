package de.htwg.wt.dominion

import com.google.inject.AbstractModule
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Cardtype
import de.htwg.wt.dominion.controller.ICardController
import de.htwg.wt.dominion.controller.maincontroller.CardController
import de.htwg.wt.dominion.model.cardComponent.ICard
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.{Card, Cardtype}
import de.htwg.wt.dominion.model.cardDatabaseComponent.ICardDatabase
import de.htwg.wt.dominion.model.cardDatabaseComponent.mongoImpl.CardMongoDbDAO
import de.htwg.wt.dominion.model.cardDatabaseComponent.slickImpl.CardMsSqlDAO
import de.htwg.wt.dominion.model.cardFileIoComponent.ICardFileIO
import de.htwg.wt.dominion.model.cardFileIoComponent.JSONImpl.FileIO
import net.codingwell.scalaguice.ScalaModule

class CardModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ICard].toInstance(Card("", "", Cardtype.KINGDOM, 0, 0, 0, 0, 0, 0, 0))
    bind[ICardController].to[CardController]
    bind[ICardFileIO].to[FileIO]
    bind[ICardDatabase].to[CardMongoDbDAO]
  }
}
