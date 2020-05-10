package de.htwg.sa.dominion.controller.maincontroller

import com.google.inject.Guice
import de.htwg.sa.dominion.DominionModule
import de.htwg.sa.dominion.controller.ControllerInterface
import de.htwg.sa.dominion.model.RoundmanagerInterface
import de.htwg.sa.dominion.model.cardcomponent.CardName
import de.htwg.sa.dominion.model.cardcomponent.CardName.CardName
import de.htwg.sa.dominion.model.roundmanagerComponent.Roundmanager
import de.htwg.sa.dominion.util.UndoManager
import javax.inject.Inject

class Controller @Inject() (var roundmanager: RoundmanagerInterface) extends ControllerInterface {

  var controllerMessage: String = ""
  var controllerState: ControllerState = PreSetupState(this)
  val undoManager = new UndoManager
  val injector = Guice.createInjector(new DominionModule)


  override def eval(input: String): Unit = {
    undoManager.doStep(new SetCommand(this))
    controllerState.evaluate(input)
    setControllerMessage(controllerState.getCurrentControllerMessage)
    notifyObservers
  }

  override def undo(): Unit = {
    undoManager.undoStep()
    notifyObservers
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers
  }

  override def getControllerMessage: String = {
    controllerMessage
  }

  override def setControllerMessage(message: String): Unit = {
    controllerMessage = message
  }

  override def getHelpPage(): Unit = {
    // TODO print helpscreen
    notifyObservers
  }

  object Controller {
    def toInt(s:String): Option[Int] = {
      try {
        Some(s.toInt)
      } catch {
        case _: Exception => None
      }
    }
  }

  trait ControllerState {
    def evaluate(input:String): Unit

    def getCurrentControllerMessage: String

    def nextState: ControllerState
  }

  case class PreSetupState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      val number = Controller.toInt(input)
      if (number.isEmpty) return
      if (number.get < 3 || number.get > 5) return

      val initCardNames: List[CardName] = List(CardName.COPPER, CardName.SILVER, CardName.GOLD, CardName.ESTATE,
        CardName.DUCHY, CardName.PROVINCE, CardName.VILLAGE, CardName.FESTIVAL, CardName.CELLAR, CardName.MINE,
        CardName.SMITHY, CardName.REMODEL, CardName.MERCHANT, CardName.WORKSHOP, CardName.GARDENS, CardName.MARKET)

      initCardNames.foreach(x => controller.roundmanager = controller.roundmanager.createPlayingDecks(x))

      // TODO player init & state change
    }

    override def getCurrentControllerMessage: String = "Welcome to Dominion\n Please enter the number of Players, must be between 3 & 5!:"

    override def nextState: ControllerState = PlayerSetupState(controller)
  }

  case class PlayerSetupState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      // TODO setup players with name and decks n´shit & state change

    }

    override def getCurrentControllerMessage: String = ???

    override def nextState: ControllerState = InGameState(controller)
  }

  case class InGameState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = {
      // TODO add game lokik to roundmanager
    }

    override def getCurrentControllerMessage: String = ???

    override def nextState: ControllerState = GameOverState(controller)
  }

  case class GameOverState(controller: Controller) extends ControllerState {
    override def evaluate(input: String): Unit = ()

    override def getCurrentControllerMessage: String = ???

    override def nextState: ControllerState = this
  }
}
