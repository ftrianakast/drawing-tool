package co.hg.drawingtool.domain.canvas

import co.hg.drawingtool.domain.common.Coordinate

case class Cell(coordinate: Coordinate, filler: Option[String])