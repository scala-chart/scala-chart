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

import java.io.OutputStream

import com.lowagie.text.{ Document, Rectangle }
import com.lowagie.text.pdf.{ DefaultFontMapper, FontMapper, PdfWriter }

/** Provides methods for writing a chart to an `OutputStream`.
  *
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
trait WritableChart extends EncodableChart {

  self: Chart[_] ⇒

  /** Writes the chart as a PDF.
    *
    * @param os         stream to where will be written
    * @param dim        $dim
    * @param fontMapper $fontMapper
    */
  def writeAsPDF(os: OutputStream, dim: (Int,Int), fontMapper: FontMapper = new DefaultFontMapper) {
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
