package scalax.chart
package module

import language.implicitConversions

import exporting._

import scala.swing.Orientable

import org.jfree.chart._
import org.jfree.chart.plot._

import Imports.Orientation

/** $RichChartInfo */
object RichChart extends RichChart

/** $RichChartInfo
  *
  * @define RichChartInfo Contains an enriched `JFreeChart` that provides convenient access to
  * e.g. save and show the chart. To read the documentation for these methods, see
  * [[GenericRichChart]].
  */
trait RichChart {

  /** Enriched `JFreeChart`. */
  implicit class GenericRichChart(val peer: JFreeChart) extends Chart with Orientable {

    type Plot = org.jfree.chart.plot.Plot

    override def plot: Plot = peer.getPlot

    override def orientation: Orientation = plot match {
      case plot: CategoryPlot ⇒ plot.getOrientation
      case plot: XYPlot       ⇒ plot.getOrientation
      case plot ⇒ throw new UnsupportedOperationException (
        s"""The underlying ${plot.getPlotType} is not orientable."""
      )
    }

    override def orientation_=(orientation: Orientation): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.setOrientation(orientation)
      case plot: XYPlot       ⇒ plot.setOrientation(orientation)
      case plot ⇒ throw new UnsupportedOperationException (
        s"""The underlying ${plot.getPlotType} is not orientable."""
      )
    }

  }

  implicit def JFreeChartJPEGExporter(chart: JFreeChart) =
    new JPEGExporter(chart)

  implicit def JFreeChartPDFExporter(chart: JFreeChart) =
    new PDFExporter(chart)

  implicit def JFreeChartPNGExporter(chart: JFreeChart) =
    new PNGExporter(chart)

}
