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

import java.io.FileOutputStream

import com.lowagie.text.pdf.{ DefaultFontMapper, FontMapper }

/** Provides methods for saving a chart.
  *
  * @define file the output file
  */
trait StorableChart extends WritableChart {

  self: Chart[_] ⇒

  private def managed[R <: FileOutputStream](r: R)(f: R ⇒ Unit): Unit = try { f(r) } finally { r.close() }

  /** Saves the chart as a JPEG image.
    *
    * @param file $file
    * @param dim  $dim
    */
  def saveAsJPEG(file: String, dim: (Int,Int)) {
    managed(new FileOutputStream(file)) { os ⇒ writeAsJPEG(os, dim) }
  }

  /** Saves the chart as a PDF.
    *
    * @param file       $file
    * @param dim        $dim
    * @param fontMapper $fontMapper
    */
  def saveAsPDF(file: String, dim: (Int,Int), fontMapper: FontMapper = new DefaultFontMapper) {
    managed(new FileOutputStream(file)) { os ⇒ writeAsPDF(os, dim, fontMapper) }
  }

  /** Saves the chart as a PNG image.
    *
    * @param file $file
    * @param dim  $dim
    */
  def saveAsPNG(file: String, dim: (Int,Int)) {
    managed(new FileOutputStream(file)) { os ⇒ writeAsPNG(os, dim) }
  }

}
