package de.htwg.sa.dominion.controller.maincontroller

import com.google.inject.Inject
import de.htwg.sa.dominion.controller.ICardController
import de.htwg.sa.dominion.model.cardComponent.ICard
import de.htwg.sa.dominion.model.fileIoComponent.ICardFileIO

import scala.util.{Failure, Success}

class CardController @Inject()(var card: ICard, fileIO: ICardFileIO) extends ICardController {
  override def save(): Unit = {
    fileIO.save(card, "cardModule")
  }

  override def load(): Unit = {
    card = fileIO.load(card, "cardModule") match {
      case Failure(_) => return
      case Success(value) => value
    }
  }

  override def constructCardNameString(): String = card.constructCardNameString()

  override def constructCardInfoString(): String = card.constructCardInformationString
}
