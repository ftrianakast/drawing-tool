package co.hg.drawingtool.utils

import java.io.InputStream
import scala.io.Source._


object FileReader {
  def readFile(fileName: String): List[String] = {
    val inputFileName: String = fileName
    val inputStream: InputStream = getClass.getResourceAsStream(inputFileName)
    val lines: List[String] = fromInputStream(inputStream).getLines().toList
    lines
  }
}
