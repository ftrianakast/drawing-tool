package co.hg.drawingtool.domain.drawer

import co.hg.drawingtool.exceptions.{DomainException, BuildException, DrawException}
import co.hg.drawingtool.domain.canvas.{Cell, Canvas}
import co.hg.drawingtool.domain.common.{DrawableElement, Coordinate}
import co.hg.drawingtool.domain.graphics.{Color, Rectangle, Line}
import scalaz._
import scalaz.Scalaz._


/**
 * Point domain entry that exposes the utilities for drawing and canvas creation
 */
object Drawer {

  def createCanvas(width: Int, height: Int): ValidationNel[DomainException, Canvas] = {
    val cells: List[Cell] = {
      def calculatePositions: List[(Int, Int)] = for {
        x <- (1 to width).toList
        y <- (1 to height).toList
      } yield (x, y)
      calculatePositions.map { case (x, y) => Cell(Coordinate(x, y), None) }
    }
    Canvas(width, height, cells)
  }

  def drawLine(line: Line, currentCanvas: Canvas): ValidationNel[DomainException, Canvas] = {
    val isLineInCanvas = currentCanvas.isABoundedElement(line)
    val isAnAllowedLine = line.isHorizontal || line.isAPoint || line.isVertical
    if (isAnAllowedLine && isLineInCanvas) drawElement(line, currentCanvas).flatMap(_.successNel)
    else new DrawException("Its not allowed to draw the line. Its only allowed diagonal " +
      "lines and lines that will be bounded by the canvas").failureNel
  }


  def drawRectangle(rectangle: Rectangle, currentCanvas: Canvas): ValidationNel[DomainException, Canvas] = {
    val isRectangleInCanvas = currentCanvas.isABoundedElement(rectangle)
    if (isRectangleInCanvas) drawElement(rectangle, currentCanvas).flatMap(_.successNel)
    else new DrawException("The rectangle that you are trying to draw its not bounded by the canvas").failureNel
  }

  def bucketFill(color: Color, startPoint: Coordinate, currentCanvas: Canvas): ValidationNel[DomainException, Canvas] = {
    val isColorInCanvas = currentCanvas.isABoundedCoordinate(startPoint)
    if (isColorInCanvas) BucketFill.colourArea(currentCanvas, currentCanvas.findCell(startPoint).get, color).flatMap(_.successNel)
    else new DrawException("The rectangle that you are trying to draw its not bounded by the canvas").failureNel
  }


  private def drawElement(element: DrawableElement, currentCanvas: Canvas): ValidationNel[BuildException, Canvas] = {
    val elementCells: List[Cell] = CellsFinder.findCellsThatConformsDrawableElement(element, currentCanvas)
      .map(cell => Cell(cell.coordinate, Some(element.drawUnit)))
    val newCells: List[Cell] =
      (elementCells ++ currentCanvas.cells)
        .groupBy(_.coordinate)
        .map(_._2.head)
        .toList
        .sortBy(cell => (cell.coordinate.positionX, cell.coordinate.positionY))
    Canvas(currentCanvas.width, currentCanvas.height, newCells)
  }

}
