package checkers

import language.experimental.macros
import scala.reflect.macros.blackbox

package object consts {
  type Color = Int

  def DARK: Color = macro darkImpl
  def LIGHT: Color = macro lightImpl

  type PieceType = Int

  def MAN: PieceType = macro manImpl
  def KING: PieceType = macro kingImpl

  type Occupant = Int

  def EMPTY: Occupant = macro emptyImpl
  def LIGHTMAN: Occupant  = macro lightManImpl
  def DARKMAN: Occupant  = macro darkManImpl
  def LIGHTKING: Occupant  = macro lightKingImpl
  def DARKKING: Occupant  = macro darkKingImpl

  val MoveListFrameSize = 12

  def MOVELISTFRAMESIZE: Int = macro moveListFrameSizeImpl

  def COLOR(occupant: Occupant): Color = macro colorImpl
  def PIECETYPE(occupant: Occupant): PieceType = macro pieceTypeImpl
  def ISPIECE(occupant: Occupant): Boolean = macro isPieceImpl
  def ISEMPTY(occupant: Occupant): Boolean = macro isEmptyImpl
  def OPPONENT(color: Color): Color = macro opponentImpl

  def darkImpl(c: blackbox.Context): c.Expr[Color] = c.universe.reify(0)
  def lightImpl(c: blackbox.Context): c.Expr[Color] = c.universe.reify(1)

  def manImpl(c: blackbox.Context): c.Expr[PieceType] = c.universe.reify(0)
  def kingImpl(c: blackbox.Context): c.Expr[PieceType] = c.universe.reify(1)

  def emptyImpl(c: blackbox.Context): c.Expr[Occupant] = c.universe.reify(0)
  def darkManImpl(c: blackbox.Context): c.Expr[Occupant] = c.universe.reify(1)
  def lightManImpl(c: blackbox.Context): c.Expr[Occupant] = c.universe.reify(2)
  def darkKingImpl(c: blackbox.Context): c.Expr[Occupant] = c.universe.reify(5)
  def lightKingImpl(c: blackbox.Context): c.Expr[Occupant] = c.universe.reify(6)


  def moveListFrameSizeImpl(c: blackbox.Context): c.Expr[Int] = {
    import c.universe._
    c.Expr[Int](Literal(Constant(MoveListFrameSize)))
  }

  def colorImpl(c: blackbox.Context)(occupant: c.Expr[Occupant]): c.Expr[Color] = {
    import c.universe._
    c.Expr[Color](q"($occupant >> 1) & 1")
  }

  def opponentImpl(c: blackbox.Context)(color: c.Expr[Color]): c.Expr[Color] = {
    import c.universe._
    c.Expr[Color](q"(~$color) & 1")
  }

  def pieceTypeImpl(c: blackbox.Context)(occupant: c.Expr[Occupant]): c.Expr[PieceType] = {
    import c.universe._
    c.Expr[PieceType](q"($occupant >> 2) & 1")
  }

  def isEmptyImpl(c: blackbox.Context)(occupant: c.Expr[Occupant]): c.Expr[Boolean] = {
    import c.universe._
    c.Expr[Boolean](q"$occupant == 0")
  }

  def isPieceImpl(c: blackbox.Context)(occupant: c.Expr[Occupant]): c.Expr[Boolean] = {
    import c.universe._
    c.Expr[Boolean](q"$occupant > 0")
  }

}