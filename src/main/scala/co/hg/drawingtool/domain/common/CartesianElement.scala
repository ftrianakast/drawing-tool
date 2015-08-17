package co.hg.drawingtool.domain.common

trait CartesianElement {
  def listVertexCoordinates: List[Coordinate]
  def listCoordinates: List[Coordinate]
}

trait DrawableElement extends CartesianElement {
  def drawUnit: String
}
