package co.hg.drawingtool.domain

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.drawer.Drawer
import co.hg.drawingtool.exceptions.{BuildException, DomainException}
import org.scalatest.{Matchers, FlatSpec}

import scalaz._

class CanvasCreationTest extends FlatSpec with Matchers {
  it should "fails because its trying to create a canvas with incorrect dimension" in {
    val canvas: ValidationNel[DomainException, Canvas] = Drawer.createCanvas(-2, -3)
    canvas match {
      case Failure(exception) =>
        exception.last shouldBe a[BuildException]
      case _ =>
        fail()
    }
  }

  it should "create canvas with a correct height and width" in {
    val canvas: ValidationNel[DomainException, Canvas] = Drawer.createCanvas(5, 4)
    canvas match {
      case Success(c) =>
        c.width shouldBe 5
        c.height shouldBe 4
        c.cells.size shouldBe 20
      case _ =>
        fail()
    }
  }
}
