package checkers.components.piece

import checkers.consts._
import checkers.core.Board
import checkers.geometry.Point
import checkers.util.Easing
import japgolly.scalajs.react._

object RemovingPieceAnimation {

  case class Props(piece: Occupant,
                   fromSquare: Int,
                   progress: Double)

  class RemovingAnimationBackend($: BackendScope[Props, Unit]) {
    def render(props: Props) = {
      val t = Easing.easeInQuad(props.progress)
      val ptA = startingPoint(props.piece, props.fromSquare)
      val ptB = AnimationHelpers.exitPoint(props.piece, props.fromSquare)

      val x0 = ptA.x
      val x = x0 + t * (ptB.x - x0)

      val y0 = ptA.y
      val y = y0 + t * (ptB.y - y0)

      val physicalPieceProps = PhysicalPieceProps.default.copy(piece = props.piece,
        x = x,
        y = y)

      val physicalPiece = PhysicalPiece.apply(physicalPieceProps)

      physicalPiece
    }
  }


  val component = ReactComponentB[Props]("RemovingPieceAnimation")
    .renderBackend[RemovingAnimationBackend]
    .shouldComponentUpdateCB { case ShouldComponentUpdate(scope, nextProps, _) =>
      val result = scope.props != nextProps
      CallbackTo.pure(result)
    }
    .build

  def apply(props: Props) = component(props)

  private def startingPoint(piece: Occupant, fromSquare: Int): Point = {
    Board.squareCenter(fromSquare)
  }


}