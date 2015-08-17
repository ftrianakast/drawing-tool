package co.hg.drawingtool.domain.utils

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.common.Coordinate
import org.scalatest.{Matchers, FlatSpec}


object CommonTest extends FlatSpec with Matchers {
  def testThatAPointConformsAFigure(point: Coordinate, canvas: Canvas, representation: String) = {
    canvas.findCell(point) match {
      case Some(cell) =>
        cell.filler shouldBe Some(representation)
      case _ =>
        fail()
    }
  }
}
