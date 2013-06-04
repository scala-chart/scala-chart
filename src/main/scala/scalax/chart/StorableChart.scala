/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012-2013 Christian Krause                                                       *
 *                                                                                               *
 *  Christian Krause <kizkizzbangbang@googlemail.com>                                            *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of 'scala-chart'.                                                          *
 *                                                                                               *
 *  This project is free software: you can redistribute it and/or modify it under the terms      *
 *  of the GNU Lesser General Public License as published by the Free Software Foundation,       *
 *  either version 3 of the License, or any later version.                                       *
 *                                                                                               *
 *  This project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;    *
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    *
 *  See the GNU Lesser General Public License for more details.                                  *
 *                                                                                               *
 *  You should have received a copy of the GNU Lesser General Public License along with this     *
 *  project. If not, see <http://www.gnu.org/licenses/>.                                         *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package scalax.chart

import java.io._

import org.jfree.chart._

/** Provides methods for saving a chart.
  *
  * @define output     the output file
  * @define dim        dimension / geometry / width x height of the output
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
trait StorableChart {

  self: Chart[_] ⇒

  /** Saves the chart as a PNG image.
    *
    * @param output $output
    * @param dim    $dim
    */
  def saveAsPNG(output: File, dim: (Int,Int)) {
    val (width,height) = dim
    ChartUtilities.saveChartAsPNG(output, peer, width, height)
  }

  /** Returns the image encoded as PNG in a byte array
   * @param dim     $dim
   */
  def encodeAsPNG(dim: (Int, Int)): Array[Byte] = {
    val (width, height) = dim
    ChartUtilities.encodeAsPNG(peer.createBufferedImage(width, height))
  }

  /** Returns the image encoded as PNG in a byte array
   * @param dim           $dim
   * @param encodeAlpha   should encode alpha channel
   * @param compression   the PNG compression level (0-9)
   */
  def encodeAsPNG(dim: (Int, Int), encodeAlpha: Boolean, compression: Int): Array[Byte] = {
    val (width, height) = dim
    ChartUtilities.encodeAsPNG(
      peer.createBufferedImage(width, height),
      encodeAlpha,
      compression)
  }

  /** Saves the chart as a JPEG image.
    *
    * @param output $output
    * @param dim    $dim
    */
  def saveAsJPEG(output: File, dim: (Int,Int)) {
    val (width,height) = dim
    ChartUtilities.saveChartAsJPEG(output, peer, width, height)
  }

  import com.lowagie.text._
  import com.lowagie.text.pdf._

  /** Saves the chart as a PDF.
    *
    * @param output     $output
    * @param dim        $dim
    * @param fontMapper $fontMapper
    */
  def saveAsPDF(output: File, dim: (Int,Int), fontMapper: FontMapper = new DefaultFontMapper) {
    val os = new BufferedOutputStream(new FileOutputStream(output))

    try {
      writeAsPDF(os, dim, fontMapper)
    } finally {
      os.close()
    }
  }

  /** Writes the chart as a PDF.
    *
    * @param os         stream to where will be written
    * @param dim        $dim
    * @param fontMapper $fontMapper
    */
  protected def writeAsPDF(os: OutputStream, dim: (Int,Int), fontMapper: FontMapper) {
    val (width,height) = dim

    val pagesize = new Rectangle(width, height)
    val document = new Document(pagesize)

    try {
      val writer = PdfWriter.getInstance(document, os)
      document.open()

      val cb = writer.getDirectContent
      val tp = cb.createTemplate(width, height)
      val g2 = tp.createGraphics(width, height, fontMapper)
      val r2D = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)

      peer.draw(g2, r2D)
      g2.dispose()
      cb.addTemplate(tp, 0, 0)
    } finally {
      document.close()
    }
  }

}
