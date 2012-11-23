/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012 Christian Krause                                                            *
 *                                                                                               *
 *  Christian Krause <kizkizzbangbang@googlemail.com>                                            *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of 'sfreechart'.                                                           *
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


package org.sfree.chart

import java.io._
import javax.swing.SwingUtilities

import org.jfree.chart._

/** $RichChartInfo */
object RichChart extends RichChart

/** $RichChartInfo
  *
  * @define RichChartInfo Contains an enriched `JFreeChart` that provides convenient access to
  * e.g. save and show the chart. To read the documentation for these methods, see
  * [[org.sfree.chart.RichChart.RichChart]].
  *
  * @define output     the output file
  * @define dim        dimension / geometry / width x height of the output
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
trait RichChart {

  /** Enriched JFreeChart. */
  implicit class RichChart(chart: JFreeChart) {

    /** Shows the chart in a window. */
    def show { show() }

    /** Shows the chart in a window.
      *
      * @param title the title of the enclosing frame
      * @param scrollable whether the enclosing panel is scrollable
      */
    def show(title: String = "", scrollable: Boolean = true) = SwingUtilities invokeLater {
      new Runnable {
        def run = new ChartFrame(title, chart, scrollable) setVisible true
      }
    }

    /** Saves the chart.
      *
      * @param ext extension of the file / output type, currently supported are PNG, JPEG and PDF
      * @param output $output
      * @param dim $dim
      */
    def save(ext: String, output: File, dim: (Int,Int)): Unit = ext.toLowerCase match {
      case "pdf"          ⇒ saveAsPDF(output, dim)
      case "png"          ⇒ saveAsPNG(output, dim)
      case "jpg" | "jpeg" ⇒ saveAsJPEG(output, dim)
      case _              ⇒ throw new RuntimeException("""Extension "%s" is not supported.""".format(ext))
    }

    /** Saves the chart as a PNG image.
      *
      * @param output $output
      * @param dim $dim
      */
    def saveAsPNG(output: File, dim: (Int,Int)) {
      val (width,height) = dim
      ChartUtilities.saveChartAsPNG(output, chart, width, height)
    }

    /** Saves the chart as a JPEG image.
      *
      * @param output $output
      * @param dim $dim
      */
    def saveAsJPEG(output: File, dim: (Int,Int)) {
      val (width,height) = dim
      ChartUtilities.saveChartAsJPEG(output, chart, width, height)
    }

    import java.awt.geom._
    import com.lowagie.text._
    import com.lowagie.text.pdf._

    /** Saves the chart as a PDF.
      *
      * @param output $output
      * @param dim $dim
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
      * @param os stream to where will be written
      * @param dim $dim
      * @param fontMapper $fontMapper
      */
    def writeAsPDF(os: OutputStream, dim: (Int,Int), fontMapper: FontMapper = new DefaultFontMapper) {
      val (width,height) = dim

      val pagesize = new Rectangle(width, height)
      val document = new Document(pagesize, 50, 50, 50, 50)

      try {
        val writer = PdfWriter.getInstance(document, os)
        document.open()

        val cb = writer.getDirectContent
        val tp = cb.createTemplate(width, height)
        val g2 = tp.createGraphics(width, height, fontMapper)
        val r2D = new Rectangle2D.Double(0, 0, width, height)

        chart.draw(g2, r2D)
        g2.dispose()
        cb.addTemplate(tp, 0, 0)
      } finally {
        document.close()
      }
    }

  }

}
