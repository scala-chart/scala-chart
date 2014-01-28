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
