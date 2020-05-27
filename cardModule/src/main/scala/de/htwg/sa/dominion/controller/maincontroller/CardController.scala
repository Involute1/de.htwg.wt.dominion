package de.htwg.sa.dominion.controller.maincontroller

import com.google.inject.Inject
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.ICardFileIO
// , fileIO: ICardFileIO
class CardController @Inject()(var card: ICard) extends ICardController {
  override def save(): Unit = ???

  override def load(): Unit = ???

  override def constructCardNameString(): String = card.constructCardNameString()

  override def constructCardInfoString(): String = card.constructCardInformationString
}
