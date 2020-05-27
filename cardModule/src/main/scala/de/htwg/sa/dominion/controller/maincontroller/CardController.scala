package de.htwg.sa.dominion.controller.maincontroller

import com.google.inject.Inject
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.IFileIO

class CardController @Inject()(var card: ICard, fileIO: IFileIO) extends ICardController {
  override def save(): Unit = ???

  override def load(): Unit = ???

  override def constructCardNameString(): String = card.constructCardNameString()

  override def constructCardInfoString(): String = card.constructCardInformationString
}
