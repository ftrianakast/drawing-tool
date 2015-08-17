package co.hg.drawingtool.app

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.exceptions.{DomainException, PreconditionException}
import co.hg.drawingtool.utils.FileReader
import scalaz.Scalaz._
import scalaz._

/**
 * Entry point
 */
object Application extends App {

  val lines: List[String] = FileReader.readFile("/input.txt")
  val result: ValidationNel[DomainException, Result] = extractAndExecuteCommands(lines)
  printResult(result)


  /**
   *
   * @param lines: File lines that contains the codified instructions for drawing
   * @return
   */
  private def extractAndExecuteCommands(lines: List[String]): ValidationNel[DomainException, Result] = {
    val commands: List[List[String]] = lines.map(line => line.split(" ").toList)
    val result: ValidationNel[DomainException, Result] =
      if (commands.head.head.equals("C")) CommanderExecutor.executeCommands(commands).successNel
      else new PreconditionException("You must create first a Canvas").failureNel
    result
  }


  /**
   * It allows to print an ASCII representation of a drawing canvas process
   * @param res: Its the result to be printed in console
   */
  private def printResult(res: ValidationNel[DomainException, Result]): Unit = {
    res match {
      case Success(r) =>
        r.partialCanvas.foreach(printComputedCanvas)

      case Failure(f) =>
        f.foreach(println)
    }
    def printComputedCanvas(computedCanvas: ValidationNel[DomainException, Canvas]): Unit = {
      computedCanvas match {
        case Success(c) => println(c)
        case Failure(f) => f.foreach(println)
      }
    }
  }
}
