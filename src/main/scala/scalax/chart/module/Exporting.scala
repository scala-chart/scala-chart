package scalax.chart
package module

import language.implicitConversions

import exporting._

/** $ExportingInfo */
object Exporting extends Exporting

/** $ExportingInfo
  *
  * @define ExportingShortInfo [[Exporting]] contains enrichments to conveniently export charts to
  * disk.
  *
  * {{{
  * chart.saveAsPNG("/tmp/chart.png")
  * }}}
  *
  * @define ExportingInfo '' '' $ExportingShortInfo
  *
  * == Supported Formats ==
  *
  * Charts may be exported into the following formats:
  *
  *  - JPEG
  *  - PNG
  *  - PDF, needs the optional dependency `com.lowagie.itext` on your class path
  *  - SVG, needs the optional dependency `org.jfree.jfreesvg` on your class path
  *
  * == Export Stages ==
  *
  * There are different stages in which charts may be exported: encoding, writing and the actual
  * saving of the file, which is demonstrated by the following code snippet:
  *
  * {{{
  * val bytes = chart.encodeAsPNG()
  *
  * chart.writeAsPNG(System.out)
  *
  * chart.saveAsPNG("/tmp/chart.png")
  * }}}
  */
trait Exporting {

  implicit def ChartJPEGExporter(chart: Chart): JPEGExporter =
    new JPEGExporter(chart)

  implicit def ChartPDFExporter(chart: Chart): PDFExporter =
    new PDFExporter(chart)

  implicit def ChartPNGExporter(chart: Chart): PNGExporter =
    new PNGExporter(chart)

  implicit def ChartSVGExporter(chart: Chart): SVGExporter =
    new SVGExporter(chart)

}
