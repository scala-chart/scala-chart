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

import org.jfree.chart._
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.plot._

/** $RichChartInfo */
object RichChart extends RichChart

/** $RichChartInfo
  *
  * @define RichChartInfo Contains an enriched `JFreeChart` that provides convenient access to
  * e.g. save and show the chart. To read the documentation for these methods, see
  * [[org.sfree.chart.RichChart.GenericRichChart]].
  */
trait RichChart {

  /** Enriched JFreeChart. */
  implicit class GenericRichChart(val peer: JFreeChart) extends Chart[Plot] with Orientable with DomainAxis with RangeAxis {

    override def plot: Plot = peer.getPlot

    // ---------------------------------------------------------------------------------------------
    // accessors / mutators
    // ---------------------------------------------------------------------------------------------

    override def domainAxisLabel: String = plot match {
      case plot: CategoryPlot    ⇒ plot.getDomainAxis.getLabel
      case plot: ContourPlot     ⇒ plot.getDomainAxis.getLabel
      case plot: FastScatterPlot ⇒ plot.getDomainAxis.getLabel
      case plot: XYPlot          ⇒ plot.getDomainAxis.getLabel
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) has no domain axis.".format(plot.getPlotType)
      )
    }

    override def domainAxisLabel_=(label: String): Unit = plot match {
      case plot: CategoryPlot    ⇒ plot.getDomainAxis.setLabel(label)
      case plot: ContourPlot     ⇒ plot.getDomainAxis.setLabel(label)
      case plot: FastScatterPlot ⇒ plot.getDomainAxis.setLabel(label)
      case plot: XYPlot          ⇒ plot.getDomainAxis.setLabel(label)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) has no domain axis.".format(plot.getPlotType)
      )
    }

    override def orientation: PlotOrientation = plot match {
      case plot: CategoryPlot ⇒ plot.getOrientation
      case plot: XYPlot       ⇒ plot.getOrientation
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) is not orientable.".format(plot.getPlotType)
      )
    }

    override def orientation_=(orientation: PlotOrientation): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.setOrientation(orientation)
      case plot: XYPlot       ⇒ plot.setOrientation(orientation)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) is not orientable.".format(plot.getPlotType)
      )
    }

    override def rangeAxisLabel: String = plot match {
      case plot: CategoryPlot    ⇒ plot.getRangeAxis.getLabel
      case plot: ContourPlot     ⇒ plot.getRangeAxis.getLabel
      case plot: FastScatterPlot ⇒ plot.getRangeAxis.getLabel
      case plot: ThermometerPlot ⇒ plot.getRangeAxis.getLabel
      case plot: XYPlot          ⇒ plot.getRangeAxis.getLabel
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) has no range axis.".format(plot.getPlotType)
      )
    }

    override def rangeAxisLabel_=(label: String): Unit = plot match {
      case plot: CategoryPlot    ⇒ plot.getRangeAxis.setLabel(label)
      case plot: ContourPlot     ⇒ plot.getRangeAxis.setLabel(label)
      case plot: FastScatterPlot ⇒ plot.getRangeAxis.setLabel(label)
      case plot: ThermometerPlot ⇒ plot.getRangeAxis.setLabel(label)
      case plot: XYPlot          ⇒ plot.getRangeAxis.setLabel(label)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) has no range axis.".format(plot.getPlotType)
      )
    }

    /** Returns true if the underlying plot displays tooltips. */
    def tooltips: Boolean = plot match {
      case plot: CategoryPlot    ⇒ plot.getRenderer.getBaseToolTipGenerator != null
      case plot: MultiplePiePlot ⇒ plot.getPieChart.tooltips
      case plot: PiePlot         ⇒ plot.getToolTipGenerator != null
      case plot: XYPlot          ⇒ plot.getRenderer.getBaseToolTipGenerator != null
      case _                     ⇒ false
    }

    /** Displays tooltips on mouse over events. */
    def tooltips_=(tooltips: Boolean): Unit = plot match {
      case plot: CategoryPlot if tooltips ⇒
        plot.getRenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator)

      case plot: CategoryPlot if ! tooltips ⇒
        plot.getRenderer.setBaseToolTipGenerator(null)

      case plot: MultiplePiePlot ⇒
        plot.getPieChart.tooltips = tooltips

      case plot: PiePlot if tooltips ⇒
        plot.setToolTipGenerator(new StandardPieToolTipGenerator)

      case plot: PiePlot if ! tooltips ⇒
        plot.setToolTipGenerator(null)

      case plot: XYPlot if tooltips ⇒
        val renderer = plot.getRenderer
        plot.getDomainAxis match {
          case _: DateAxis ⇒
            renderer.setBaseToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance)
          case _ ⇒
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator)
        }

      case plot: XYPlot if ! tooltips ⇒
        plot.getRenderer.setBaseToolTipGenerator(null)

      case _ ⇒ throw new UnsupportedOperationException (
        "Tool tips are not supported for the underlying type of plot."
      )
    }

  }

}
