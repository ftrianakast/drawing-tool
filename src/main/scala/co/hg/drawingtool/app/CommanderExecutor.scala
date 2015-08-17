package co.hg.drawingtool.app

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.domain.common.Coordinate
import co.hg.drawingtool.domain.drawer.Drawer
import co.hg.drawingtool.domain.graphics.{Color, Rectangle, Line}
import co.hg.drawingtool.exceptions.DomainException

import scala.collection.mutable.ListBuffer
import scalaz._


/**
 * Serves as a controller between the file commands and real commands
 */
object CommanderExecutor {


  /**
   * Executes commands for create and drawing things in a Canvas
   *
   * @param commands The method supports 4 commands:
   *                 1- C: for canvas creation
   *                 2- L: for drawing a line
   *                 3- R: for drawing a rectangle
   *                 4- B: for fill an area with a specified color
   *
   * @return Result: A result we the computed canvas and also the partial states of a Canvas
   */
  def executeCommands(commands: List[List[String]]): Result = {
    var computedCanvas: ValidationNel[DomainException, Canvas] = Canvas(1, 1, List())
    var partialCanvas: ListBuffer[ValidationNel[DomainException, Canvas]] = new ListBuffer[ValidationNel[DomainException, Canvas]]()

    for (command <- commands) {
      command match {
        case List("C", positionX, positionY) =>
          computedCanvas = Drawer.createCanvas(positionX.toInt, positionY.toInt)
          partialCanvas += computedCanvas

        case List("L", x1, y1, x2, y2) =>
          val startPoint = Coordinate(x1.toInt, y1.toInt)
          val endPoint = Coordinate(x2.toInt, y2.toInt)
          computedCanvas = computedCanvas.flatMap(c => Drawer.drawLine(Line(startPoint, endPoint), c))
          partialCanvas += computedCanvas

        case List("R", x1, y1, x2, y2) =>
          val upperLeftCorner = Coordinate(x1.toInt, y1.toInt)
          val lowerLeftCorner = Coordinate(x2.toInt, y2.toInt)
          computedCanvas = computedCanvas.flatMap(c => Drawer.drawRectangle(Rectangle(upperLeftCorner, lowerLeftCorner), c))
          partialCanvas += computedCanvas

        case List("B", x, y, color) =>
          val startPoint = Coordinate(x.toInt, y.toInt)
          computedCanvas = computedCanvas.flatMap(c => Drawer.bucketFill(Color(color), startPoint, c))
          partialCanvas += computedCanvas
      }
    }
    Result(computedCanvas, partialCanvas.toList)
  }

}
