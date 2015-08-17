package co.hg.drawingtool.domain.graphics

import co.hg.drawingtool.domain.common.{DrawableElement, Coordinate}

case class Rectangle(upperLeftCorner: Coordinate, lowerRightCorner: Coordinate) extends DrawableElement {

  val upperRightCorner = Coordinate(lowerRightCorner.positionX, upperLeftCorner.positionY)
  val lowerLeftCorner = Coordinate(upperLeftCorner.positionX, lowerRightCorner.positionY)

  override def listVertexCoordinates: List[Coordinate] = List(upperLeftCorner, upperRightCorner, lowerLeftCorner, lowerRightCorner)

  override def listCoordinates: List[Coordinate] = {
    val upperLine: Line = Line(upperLeftCorner, upperRightCorner)
    val lowLine: Line = Line(lowerLeftCorner, lowerRightCorner)
    val leftLine: Line = Line(upperLeftCorner, lowerLeftCorner)
    val rightLine: Line = Line(upperRightCorner, lowerRightCorner)
    val completeCoordinates: List[Coordinate] = upperLine.listCoordinates ++ lowLine.listCoordinates ++ leftLine.listCoordinates ++ rightLine.listCoordinates
    completeCoordinates.distinct.sortBy(c => (c.positionX, c.positionY))
  }

  override def drawUnit: String = "x"

}
