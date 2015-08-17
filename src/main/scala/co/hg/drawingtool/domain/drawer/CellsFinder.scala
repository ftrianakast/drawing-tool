package co.hg.drawingtool.domain.drawer

import co.hg.drawingtool.domain.canvas.{Cell, Canvas}
import co.hg.drawingtool.domain.common.CartesianElement

/**
 * Utilities for finding cells
 */
object CellsFinder {

  /**
   * 
   * @param cartesianElement See [[CartesianElement]]
   * @param canvas See [[Canvas]]
   * @return
   */
  def findCellsThatConformsDrawableElement(cartesianElement: CartesianElement, canvas: Canvas): List[Cell] = {
    for {
      cell <- canvas.cells
      lineCoordinate <- cartesianElement.listCoordinates
      if cell.coordinate.equals(lineCoordinate)
    } yield cell
  }

}