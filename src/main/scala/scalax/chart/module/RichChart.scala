package scalax.chart
package module

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
  implicit class GenericRichChart(val peer: JFreeChart) extends Chart[Plot]
      with Orientable with DomainAxis with RangeAxis {

    override def plot: Plot = peer.getPlot

    override def domainAxisLabel: Option[String] = {
      val label = plot match {
        case plot: CategoryPlot    ⇒ plot.getDomainAxis.getLabel
        case plot: ContourPlot     ⇒ plot.getDomainAxis.getLabel
        case plot: FastScatterPlot ⇒ plot.getDomainAxis.getLabel
        case plot: XYPlot          ⇒ plot.getDomainAxis.getLabel
        case plot ⇒ throw new UnsupportedOperationException (
          s"""The underlying ${plot.getPlotType} has no domain axis."""
        )
      }

      Option(label) filterNot { _ == "" }
    }

    override def domainAxisLabel_=(label: Option[String]) {
      val l = label.getOrElse("")

      plot match {
        case plot: CategoryPlot    ⇒ plot.getDomainAxis.setLabel(l)
        case plot: ContourPlot     ⇒ plot.getDomainAxis.setLabel(l)
        case plot: FastScatterPlot ⇒ plot.getDomainAxis.setLabel(l)
        case plot: XYPlot          ⇒ plot.getDomainAxis.setLabel(l)
        case plot ⇒ throw new UnsupportedOperationException (
          s"""The underlying ${plot.getPlotType} has no domain axis."""
        )
      }
    }

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

    override def rangeAxisLabel: Option[String] = {
      val label = plot match {
        case plot: CategoryPlot    ⇒ plot.getRangeAxis.getLabel
        case plot: ContourPlot     ⇒ plot.getRangeAxis.getLabel
        case plot: FastScatterPlot ⇒ plot.getRangeAxis.getLabel
        case plot: ThermometerPlot ⇒ plot.getRangeAxis.getLabel
        case plot: XYPlot          ⇒ plot.getRangeAxis.getLabel
        case plot ⇒ throw new UnsupportedOperationException (
          s"""The underlying ${plot.getPlotType} has no range axis."""
        )
      }

      Option(label) filterNot { _ == "" }
    }

    override def rangeAxisLabel_=(label: Option[String]) {
      val l = label.getOrElse("")

      plot match {
        case plot: CategoryPlot    ⇒ plot.getRangeAxis.setLabel(l)
        case plot: ContourPlot     ⇒ plot.getRangeAxis.setLabel(l)
        case plot: FastScatterPlot ⇒ plot.getRangeAxis.setLabel(l)
        case plot: ThermometerPlot ⇒ plot.getRangeAxis.setLabel(l)
        case plot: XYPlot          ⇒ plot.getRangeAxis.setLabel(l)
        case plot ⇒ throw new UnsupportedOperationException (
          s"""The underlying ${plot.getPlotType} has no range axis."""
        )
      }
    }

  }

}
