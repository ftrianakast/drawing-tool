package co.hg.drawingtool.domain.graphics

import co.hg.drawingtool.domain.common.{DrawableElement, Coordinate}

case class Line(startPoint: Coordinate, endPoint: Coordinate) extends DrawableElement {

  val size: Double =
    Math.sqrt(Math.pow(endPoint.positionX - startPoint.positionX, 2) + Math.pow(endPoint.positionY - startPoint.positionY, 2))

  def isHorizontal: Boolean = {
    if (startPoint.positionY == endPoint.positionY) true
    else false
  }

  def isVertical: Boolean = {
    if (startPoint.positionX == endPoint.positionX) true
    else false
  }

  def isAPoint: Boolean = {
    if (startPoint.positionY == endPoint.positionY && startPoint.positionX == endPoint.positionX) true
    else false
  }

  def isDiagonal: Boolean = {
    if (!isHorizontal && !isVertical && !isAPoint) true
    else false
  }

  override def listCoordinates: List[Coordinate] = {
    def calculatePositions(firstStart: Int, firstLimit: Int, secondStart: Int, secondLimit: Int): List[Coordinate] = for {
      x <- (firstStart to firstLimit).toList
      y <- (secondStart to secondLimit).toList
    } yield Coordinate(x, y)

    if (isVertical) calculatePositions(startPoint.positionX, startPoint.positionX, startPoint.positionY, endPoint.positionY)
    else if (isHorizontal) calculatePositions(startPoint.positionX, endPoint.positionX, startPoint.positionY, startPoint.positionY)
    else List(startPoint)
  }

  override def listVertexCoordinates: List[Coordinate] = List(startPoint, endPoint)

  override def drawUnit: String = "x"
}
