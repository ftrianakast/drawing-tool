package co.hg.drawingtool.domain.drawer

import co.hg.drawingtool.domain.canvas.{Cell, Canvas}
import co.hg.drawingtool.domain.common.Coordinate
import co.hg.drawingtool.domain.graphics.Color
import co.hg.drawingtool.exceptions.BuildException
import scalaz._


/**
 * Provides utilities for fill with color a canvas
 */
object BucketFill {

  /**
   * Fills an empty area with a given color from the a start cell
   * @param canvas See [[Canvas]]
   * @param cell Start [[Cell]] for flood fill algorithm
   * @param targetColor The desired [[Color]] for fill the cells
   * @return
   */
  def colourArea(canvas: Canvas, cell: Cell, targetColor: Color): ValidationNel[BuildException, Canvas] = {
    val canvasCells: Array[Cell] = canvas.cells.toArray

    def findCellByCoordinate(cell: Cell): Cell = canvasCells.find(_.coordinate.equals(cell.coordinate)).get

    def recursion(cell: Cell): Unit = cell.filler match {
      case Some(filler) =>
      case _ if canvas.isABoundedCoordinate(cell.coordinate) =>
        canvasCells(canvasCells.indexOf(findCellByCoordinate(cell))) = Cell(cell.coordinate, Some(targetColor.colorUnit))
        val positionX = cell.coordinate.positionX
        val positionY = cell.coordinate.positionY
        val rightCell: Option[Cell] = canvasCells.find(searchedCell => searchedCell.coordinate.equals(Coordinate(positionX + 1, positionY)))
        val leftCell: Option[Cell] = canvasCells.find(searchedCell => searchedCell.coordinate.equals(Coordinate(positionX - 1, positionY)))
        val downCell: Option[Cell] = canvasCells.find(searchedCell => searchedCell.coordinate.equals(Coordinate(positionX, positionY + 1)))
        val upCell: Option[Cell] = canvasCells.find(searchedCell => searchedCell.coordinate.equals(Coordinate(positionX, positionY - 1)))

        leftCell.flatMap(r => Some(recursion(r)))
        rightCell.flatMap(r => Some(recursion(r)))
        downCell.flatMap(r => Some(recursion(r)))
        upCell.flatMap(r => Some(recursion(r)))
    }

    recursion(cell)
    Canvas(canvas.width, canvas.height, canvasCells.toList)
  }
}
