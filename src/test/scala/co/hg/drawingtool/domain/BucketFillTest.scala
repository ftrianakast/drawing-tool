package co.hg.drawingtool.domain

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.common.Coordinate
import co.hg.drawingtool.domain.drawer.Drawer
import co.hg.drawingtool.domain.graphics.{Color, Rectangle}
import co.hg.drawingtool.domain.utils.CommonTest
import co.hg.drawingtool.exceptions.{DrawException, DomainException}
import org.scalatest.{Matchers, FlatSpec}

import scalaz._


class BucketFillTest extends FlatSpec with Matchers {

  it should "reject bucket fill because a not bounded start point" in {
    val notBoundedStartPoint: Coordinate = Coordinate(7,8)
    val rectangle: Rectangle = Rectangle(Coordinate(2,3), Coordinate(5,6))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(6, 8)
        .flatMap(c =>  Drawer.drawRectangle(rectangle, c))
        .flatMap(c => Drawer.bucketFill(Color("v"), notBoundedStartPoint, c))

    canvas match {
      case Failure(exception) =>
        exception.last shouldBe a[DrawException]
      case _ =>
        fail()
    }
  }


  it should "fill with color v a rectangle" in {
    val color: Color = Color("v")
    val startPoint: Coordinate = Coordinate(3,4)
    val rectangle: Rectangle = Rectangle(Coordinate(2,3), Coordinate(5,6))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(6, 8)
        .flatMap(c =>  Drawer.drawRectangle(rectangle, c))
        .flatMap(c => Drawer.bucketFill(Color("v"), startPoint, c))

      canvas match {
        case Success(c) =>
          CommonTest.testThatAPointConformsAFigure(startPoint, c, color.colorUnit)
          CommonTest.testThatAPointConformsAFigure(Coordinate(3,5), c, color.colorUnit)
          CommonTest.testThatAPointConformsAFigure(Coordinate(4,4), c, color.colorUnit)
          CommonTest.testThatAPointConformsAFigure(Coordinate(4,5), c, color.colorUnit)
        case Failure(e) =>
          fail()
      }

  }

}
