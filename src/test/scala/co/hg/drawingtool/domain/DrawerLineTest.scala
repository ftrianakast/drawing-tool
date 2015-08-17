package co.hg.drawingtool.domain

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.common.Coordinate
import co.hg.drawingtool.domain.drawer.Drawer
import co.hg.drawingtool.domain.graphics.Line
import co.hg.drawingtool.domain.utils.CommonTest
import co.hg.drawingtool.exceptions.{DrawException, DomainException}
import org.scalatest.{Matchers, FlatSpec}
import scalaz.{Success, Failure, ValidationNel}


class DrawerLineTest extends FlatSpec with Matchers {

  it should "reject a line drawing trial because a not bounded line" in {
    val notBoundedLine: Line = Line(Coordinate(9, 11), Coordinate(9, 15))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(5, 4)
        .flatMap(c => Drawer.drawLine(notBoundedLine, c))
    canvas match {
      case Failure(exception) =>
        exception.last shouldBe a[DrawException]
      case _ =>
        fail()
    }
  }

  it should "reject a line drawing trial because a diagonal" in {
    val diagonalLine: Line = Line(Coordinate(5, 1), Coordinate(7, 3))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(8, 6)
        .flatMap(c => Drawer.drawLine(diagonalLine, c))
    canvas match {
      case Failure(exception) =>
        exception.last shouldBe a[DrawException]
      case _ =>
        fail()
    }
  }

  it should "create successfully a line" in {
    val line: Line = Line(Coordinate(1, 3), Coordinate(5, 3))
    val canvas: ValidationNel[DomainException, Canvas] =
      Drawer.createCanvas(8, 6)
        .flatMap(c => Drawer.drawLine(line, c))
    canvas match {
      case Success(c) =>
        CommonTest.testThatAPointConformsAFigure(line.startPoint, c, line.drawUnit)
        CommonTest.testThatAPointConformsAFigure(line.endPoint, c, line.drawUnit)
      case Failure(e) =>
        fail()
    }
  }


}
