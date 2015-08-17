package co.hg.drawingtool.domain.graphics


case class Color(colorUnit: String) {
  require(!colorUnit.equals(""))
}