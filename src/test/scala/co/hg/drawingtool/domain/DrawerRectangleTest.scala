package co.hg.drawingtool.domain

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.common.Coordinate
import co.hg.drawingtool.domain.drawer.Drawer
import co.hg.drawingtool.domain.graphics.Rectangle
import co.hg.drawingtool.domain.utils.CommonTest
import co.hg.drawingtool.exceptions.{DrawException, DomainException}
import org.scalatest.{Matchers, FlatSpec}

import scalaz._

class DrawerRectangleTest extends FlatSpec with Matchers {

  it should "reject a line drawing trial because a not bounded rectangle" in {
    val notBoundedRectangle: Rectangle = Rectangle(Coordinate(5, 6), Coordinate(7, 18))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(6, 8)
        .flatMap(c => Drawer.drawRectangle(notBoundedRectangle, c))
    canvas match {
      case Failure(exception) =>
        exception.last shouldBe a[DrawException]
      case _ =>
        fail()
    }
  }

  it should "draw a rectangle correctly" in {
    val rectangle: Rectangle = Rectangle(Coordinate(2, 3), Coordinate(4, 5))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(6, 8)
        .flatMap(c => Drawer.drawRectangle(rectangle, c))
    canvas match {
      case Success(c) =>
        CommonTest.testThatAPointConformsAFigure(rectangle.upperLeftCorner, c, rectangle.drawUnit)
        CommonTest.testThatAPointConformsAFigure(rectangle.upperRightCorner, c, rectangle.drawUnit)
        CommonTest.testThatAPointConformsAFigure(rectangle.lowerLeftCorner, c, rectangle.drawUnit)
        CommonTest.testThatAPointConformsAFigure(rectangle.lowerRightCorner, c, rectangle.drawUnit)

      case Failure(e) =>
        fail()
    }
  }



}
