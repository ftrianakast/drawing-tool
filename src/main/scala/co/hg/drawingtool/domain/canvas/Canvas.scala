package co.hg.drawingtool.domain.canvas

import co.hg.drawingtool.exceptions.BuildException
import co.hg.drawingtool.domain.common.{Coordinate, CartesianElement}
import scalaz.Scalaz._

/** Factory for [[Canvas]] instances. */
object Canvas {
  def apply(width: Int, height: Int, cells: List[Cell]) = {
    if (width <= 0 || height <= 0) new BuildException("The width and height must be greater than 0").failureNel
    else new Canvas(width, height, cells).successNel
  }
}

/**
 * Canvas
 * @param width
 * @param height
 * @param cells
 */
class Canvas private(val width: Int, val height: Int, val cells: List[Cell]) {

  /**
   * Computes if a cartesian element is within this canvas
   * @param cartesianElement See [[CartesianElement]]
   * @return
   */
  def isABoundedElement(cartesianElement: CartesianElement): Boolean = {
    cartesianElement.listVertexCoordinates.forall(
      coordinate => isABoundedCoordinate(coordinate))
  }

  /**
   * Computes if a cartesian element is within this canvas
   * @param coordinate See [[Coordinate]]
   * @return
   */
  def isABoundedCoordinate(coordinate: Coordinate): Boolean = {
    coordinate.positionX >= 1 &&
      coordinate.positionX <= width &&
      coordinate.positionY >= 1 &&
      coordinate.positionY <= height
  }

  /**
   * Finds a [[Cell]] in a canvas given its [[Coordinate]]
   * @param coordinate See [[Coordinate]]
   * @return
   */
  def findCell(coordinate: Coordinate): Option[Cell] = cells.find(cell => cell.coordinate.equals(coordinate))


  override def toString: String = {
    val matrix: List[List[Cell]] = cells.groupBy(_.coordinate.positionY).values.toList.sortBy(_.head.coordinate.positionY)
    val widthLines: String = "-" * (width + 2) + sys.props("line.separator")
    def buildMatrixString = matrix.foldLeft("")((acc, cells) => acc + "|" + buildCellsString(cells) + "|" + sys.props("line.separator"))
    def buildCellsString(cells: List[Cell]) = cells.foldLeft("") {
      case (acc, Cell(_, Some(filler))) => acc + filler
      case (acc, Cell(_, _)) => acc + " "
    }
    widthLines + buildMatrixString + widthLines
  }
}