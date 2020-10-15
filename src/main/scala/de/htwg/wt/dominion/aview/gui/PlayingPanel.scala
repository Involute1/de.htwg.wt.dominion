package de.htwg.wt.dominion.aview.gui

import java.awt.{Color, Image}

import de.htwg.wt.dominion.controller.IController
import de.htwg.wt.dominion.model.cardComponent.cardBaseImpl.Card
import javax.swing.{BorderFactory, ImageIcon}

import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, FlowPanel, Font, Label, Orientation}

class PlayingPanel(controller: IController) extends BoxPanel(Orientation.Vertical) {
  preferredSize = new Dimension(1800, 1200)
  val myFont = new Font("Charlemagne Std Bold", java.awt.Font.BOLD, 15)

  val infoPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Player: " + (controller.getCurrentPlayerTurn + 1) + " (" + controller.getCurrentPlayerName + ") " +
      controller.getCurrentPhaseAsString)
    contents += new Label("Actions: " + controller.getCurrentPlayerActions)
    contents += new Label("Buys: " + controller.getCurrentPlayerBuys)
    contents += new Label("Money: " + controller.getCurrentPlayerMoney)
    contents += new Label("Turn: " + controller.getTurn)
    font = new Font("Charlemagne Std Bold", java.awt.Font.BOLD, 20)
    border = BorderFactory.createLineBorder(Color.BLACK, 2)
  }

  val deckPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += new Label {
      private val temp = CardsGraphics.cardBack
      private val resize = temp.getScaledInstance(177, 276, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
    }
    contents += new Label {
      text = "Deck Count: " + controller.getCurrentPlayerDeck.length
      font = myFont
    }
  }

  val handPanel: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    val hand: List[Card] = controller.getCurrentPlayerHand
    val labelList: List[Label] = for ((card, index) <- hand.zipWithIndex) yield new Label {
      private val temp = CardsGraphics.mapCardNameToImg(card.cardName)
      private val resize = temp.getScaledInstance(177, 276, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
      listenTo(mouse.clicks)
      reactions += {
        case _: MouseClicked =>
          controller.eval(index.toString)
      }
    }
    labelList.foreach(x => contents += x)
  }

  val playingDeckPanel: FlowPanel = new FlowPanel() {
    val playingDecks: List[List[Card]] = controller.getPlayingDecks
    val labelList: List[Label] = for ((deck, index) <- playingDecks.zipWithIndex if deck.nonEmpty) yield new Label {
      private val temp = CardsGraphics.mapCardNameToImg(deck.head.cardName)
      private val resize = temp.getScaledInstance(177, 276, java.awt.Image.SCALE_SMOOTH)
      icon = new ImageIcon(resize)
      text = "Count: " + deck.size
      font = myFont
      listenTo(mouse.clicks)
      reactions += {
        case _: MouseClicked =>
          controller.eval(index.toString)
      }
    }
    labelList.foreach(x => contents += x)
  }

  val yesButton = new Button("Yes")
  val noButton = new Button("No")
  val okButton = new Button("Okay")
  val doneButton = new Button("Done")

  val optionPanelString = new BoxPanel(Orientation.Vertical) {
    contents += new Label()
  }

  val optionPanelButtons: BoxPanel = new BoxPanel(Orientation.Horizontal) {

  }

  val optionPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += optionPanelString
    contents += optionPanelButtons
    border = BorderFactory.createLineBorder(Color.BLACK, 2)
  }

  val southPanel: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += deckPanel
    contents += handPanel
    contents += optionPanel
  }

  val nextButton = new Button("\u2192")
  val prevButton = new Button("\u2190")

  contents += new BorderPanel {
    layout(infoPanel) = BorderPanel.Position.North
    layout(playingDeckPanel) = BorderPanel.Position.Center
    layout(southPanel) = BorderPanel.Position.South
    layout(nextButton) = BorderPanel.Position.East
    layout(prevButton) = BorderPanel.Position.West
  }

  listenTo(nextButton)
  listenTo(prevButton)

  reactions += {
    case ButtonClicked(`nextButton`) => controller.eval("a")
    case ButtonClicked(`prevButton`) => controller.undo()
  }
}

object CardsGraphics {
  val cardBack: Image = new ImageIcon("src/main/resources/cards/Card_back.png").getImage
  val copper: Image = new ImageIcon("src/main/resources/cards/Copper.png").getImage
  val silver: Image = new ImageIcon("src/main/resources/cards/Silver.png").getImage
  val gold: Image = new ImageIcon("src/main/resources/cards/Gold.png").getImage
  val estate: Image = new ImageIcon("src/main/resources/cards/Estate.png").getImage
  val duchy: Image = new ImageIcon("src/main/resources/cards/Duchy.png").getImage
  val province: Image = new ImageIcon("src/main/resources/cards/Province.png").getImage
  val cellar: Image = new ImageIcon("src/main/resources/cards/Cellar.png").getImage
  val festival: Image = new ImageIcon("src/main/resources/cards/Festival.png").getImage
  val gardens: Image = new ImageIcon("src/main/resources/cards/Gardens.png").getImage
  val market: Image = new ImageIcon("src/main/resources/cards/Market.png").getImage
  val merchant: Image = new ImageIcon("src/main/resources/cards/Merchant.png").getImage
  val mine: Image = new ImageIcon("src/main/resources/cards/Mine.png").getImage
  val remodel: Image = new ImageIcon("src/main/resources/cards/Remodel.png").getImage
  val smithy: Image = new ImageIcon("src/main/resources/cards/Smithy.png").getImage
  val village: Image = new ImageIcon("src/main/resources/cards/Village.png").getImage
  val workshop: Image = new ImageIcon("src/main/resources/cards/Workshop.png").getImage

  def mapCardNameToImg(name: String): Image = {
    name match {
      case "Copper" => CardsGraphics.copper
      case "Silver" => CardsGraphics.silver
      case "Gold" => CardsGraphics.gold
      case "Estate" => CardsGraphics.estate
      case "Duchy" => CardsGraphics.duchy
      case "Province" => CardsGraphics.province
      case "Village" => CardsGraphics.village
      case "Festival" => CardsGraphics.festival
      case "Cellar" => CardsGraphics.cellar
      case "Mine" => CardsGraphics.mine
      case "Smithy" => CardsGraphics.smithy
      case "Remodel" => CardsGraphics.remodel
      case "Merchant" => CardsGraphics.merchant
      case "Workshop" => CardsGraphics.workshop
      case "Gardens" => CardsGraphics.gardens
      case "Market" => CardsGraphics.market
    }
  }
}
