package scalax.chart

import java.io.FileOutputStream

import com.lowagie.text.pdf.FontMapper

/** @define StorableChartInfo This abstraction provides ways to store charts to file. Currently
  * supported file formats are:
  *
  *  - JPEG
  *  - PNG
  *  - PDF
  *
  * The ways in which charts may be stored are split in different stages: encoding, writing and the
  * actual saving of the file. The following code shows that each stage can be used directly:
  *
  * {{{
  * val bytes = chart.encodeAsPNG()
  *
  * chart.writeAsPNG(System.out)
  *
  * chart.saveAsPNG("/tmp/chart.png")
  * }}}
  *
  * @define file the output file
  */
private[chart] trait StorableChart extends WritableChart {

  self: Chart[_] ⇒

  private def managed[R <: FileOutputStream](r: R)(f: R ⇒ Unit): Unit =
    try { f(r) } finally { r.close() }

  /** Saves the chart as a JPEG image.
    *
    * @param file $file
    * @param dim  $dim
    *
    * @usecase def saveAsJPEG(file: String): Unit
    *   @inheritdoc
    */
  def saveAsJPEG(file: String, dim: (Int,Int) = Chart.Default.Resolution): Unit =
    managed(new FileOutputStream(file)) { os ⇒ writeAsJPEG(os, dim) }

  /** Saves the chart as a PDF document.
    *
    * @param file       $file
    * @param dim        $dim
    * @param fontMapper $fontMapper
    *
    * @usecase def saveAsPDF(file: String): Unit
    *   @inheritdoc
    */
  def saveAsPDF(file: String, dim: (Int,Int) = Chart.Default.Resolution, fontMapper: FontMapper = Chart.Default.FontMapper): Unit =
    managed(new FileOutputStream(file)) { os ⇒ writeAsPDF(os, dim, fontMapper) }

  /** Saves the chart as a PNG image.
    *
    * @param file $file
    * @param dim  $dim
    *
    * @usecase def saveAsPNG(file: String): Unit
    *   @inheritdoc
    */
  def saveAsPNG(file: String, dim: (Int,Int) = Chart.Default.Resolution): Unit =
    managed(new FileOutputStream(file)) { os ⇒ writeAsPNG(os, dim) }

}
