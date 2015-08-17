package co.hg.drawingtool.app

import co.hg.drawingtool.domain.canvas.Canvas
import co.hg.drawingtool.exceptions.DomainException

import scalaz.ValidationNel

/**
 * Serves as a structure to store and query result of a drawing canvas process
 * @param computedCanvas: the final computed Canvas
 * @param partialCanvas: the canvas steps to achieve the final computed canvas
 */
case class Result(computedCanvas: ValidationNel[DomainException, Canvas], partialCanvas:  List[ValidationNel[DomainException, Canvas]])
