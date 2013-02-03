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

import scala.swing.Orientable

import org.jfree.chart._
import org.jfree.chart.axis._
import org.jfree.chart.plot._

import Imports.Orientation

/** $RichChartInfo */
object RichChart extends RichChart

/** $RichChartInfo
  *
  * @define RichChartInfo Contains an enriched `JFreeChart` that provides convenient access to
  * e.g. save and show the chart. To read the documentation for these methods, see
  * [[scalax.chart.RichChart.GenericRichChart]].
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
          "The underlying plot (%s) has no domain axis.".format(plot.getPlotType)
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
          "The underlying plot (%s) has no domain axis.".format(plot.getPlotType)
        )
      }
    }

    override def orientation: Orientation = plot match {
      case plot: CategoryPlot ⇒ plot.getOrientation
      case plot: XYPlot       ⇒ plot.getOrientation
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) is not orientable.".format(plot.getPlotType)
      )
    }

    override def orientation_=(orientation: Orientation): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.setOrientation(orientation)
      case plot: XYPlot       ⇒ plot.setOrientation(orientation)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) is not orientable.".format(plot.getPlotType)
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
          "The underlying plot (%s) has no range axis.".format(plot.getPlotType)
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
          "The underlying plot (%s) has no range axis.".format(plot.getPlotType)
        )
      }
    }

  }

}
