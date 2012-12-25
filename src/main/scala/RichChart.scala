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
import org.jfree.chart.title._

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
  implicit class GenericRichChart(val peer: JFreeChart) extends Chart[Plot] {

    override def plot = peer.getPlot

    // ---------------------------------------------------------------------------------------------
    // accessors / mutators
    // ---------------------------------------------------------------------------------------------

    /** Optionally returns the domain axis label of the underlying plot. */
    def domainAxisLabel: Option[String] = plot match {
      case plot: CategoryPlot    ⇒ Option(plot.getDomainAxis.getLabel)
      case plot: ContourPlot     ⇒ Option(plot.getDomainAxis.getLabel)
      case plot: FastScatterPlot ⇒ Option(plot.getDomainAxis.getLabel)
      case plot: XYPlot          ⇒ Option(plot.getDomainAxis.getLabel)
      case _                     ⇒ None
    }

    /** Labels the domain axis of the underlying plot. */
    def domainAxisLabel_=(label: String): Unit = plot match {
      case plot: CategoryPlot    ⇒ plot.getDomainAxis.setLabel(label)
      case plot: ContourPlot     ⇒ plot.getDomainAxis.setLabel(label)
      case plot: FastScatterPlot ⇒ plot.getDomainAxis.setLabel(label)
      case plot: XYPlot          ⇒ plot.getDomainAxis.setLabel(label)
      case _ ⇒ throw new UnsupportedOperationException (
        "Domain axis labels are not supported for the underlying type of plot."
      )
    }

    /** Returns true if this chart displays labels. */
    def labels: Boolean = plot match {
      case plot: CategoryPlot    ⇒ plot.getRenderer.getBaseItemLabelsVisible
      case plot: PiePlot         ⇒ plot.getLabelGenerator != null
      case plot: MultiplePiePlot ⇒ plot.getPieChart.labels
      case plot: XYPlot          ⇒ plot.getRenderer.getBaseItemLabelsVisible
      case _                     ⇒ false
    }

    /** Labels the discrete data points of this chart. */
    def labels_=(labels: Boolean): Unit = plot match {
      case plot: CategoryPlot if labels ⇒
        val renderer = plot.getRenderer
        renderer.setBaseItemLabelsVisible(true)
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator)

      case plot: CategoryPlot if ! labels ⇒
        val renderer = plot.getRenderer
        renderer.setBaseItemLabelsVisible(false)

      case plot: PiePlot if labels ⇒
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator)

      case plot: PiePlot if ! labels ⇒
        plot.setLabelGenerator(null)

      case plot: MultiplePiePlot ⇒
        plot.getPieChart.labels = labels

      case plot: XYPlot if labels ⇒
        val renderer = plot.getRenderer
        renderer.setBaseItemLabelsVisible(true)
        renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator)

      case plot: XYPlot if ! labels ⇒
        val renderer = plot.getRenderer
        renderer.setBaseItemLabelsVisible(false)

      case _ ⇒ throw new UnsupportedOperationException (
        "Labels are not supported for the underlying type of plot."
      )
    }

    /** Optionally returns the legend of this chart. */
    def legend: Option[LegendTitle] = peer.getLegend match {
      case legend: LegendTitle ⇒ Some(legend)
      case _                   ⇒ None
    }

    /** Adds a default legend if there is none. */
    def legend_=(legend: Boolean): Unit = if (legend) {
      if (this.legend.isEmpty) {
        val legend = new LegendTitle(plot)
        legend.setMargin(new org.jfree.ui.RectangleInsets(1.0, 1.0, 1.0, 1.0))
        legend.setFrame(new org.jfree.chart.block.LineBorder)
        legend.setBackgroundPaint(java.awt.Color.white)
        legend.setPosition(org.jfree.ui.RectangleEdge.BOTTOM)
        peer.addSubtitle(legend)
        legend.addChangeListener(peer)
      }
    } else {
      peer.removeLegend()
    }

    /** Adds a single legend. */
    def legend_=(legend: LegendTitle) {
      if (this.legend.nonEmpty) peer.removeLegend()
      peer.addSubtitle(legend)
      legend.addChangeListener(peer)
    }

    /** Optionally returns the orientation of the underlying plot. */
    def orientation: Option[PlotOrientation] = plot match {
      case plot: CategoryPlot    ⇒ Some(plot.getOrientation)
      case plot: FastScatterPlot ⇒ Some(plot.getOrientation)
      case plot: PolarPlot       ⇒ Some(plot.getOrientation)
      case plot: ThermometerPlot ⇒ Some(plot.getOrientation)
      case plot: XYPlot          ⇒ Some(plot.getOrientation)
      case _                     ⇒ None
    }

    /** Orients the underlying plot. */
    def orientation_=(orientation: PlotOrientation): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.setOrientation(orientation)
      case plot: XYPlot       ⇒ plot.setOrientation(orientation)
      case _ ⇒ throw new UnsupportedOperationException (
        "Orienting the underlying type of plot is not supported."
      )
    }

    /** Optionally returns the range axis label of the underlying plot. */
    def rangeAxisLabel: Option[String] = plot match {
      case plot: CategoryPlot    ⇒ Option(plot.getRangeAxis.getLabel)
      case plot: ContourPlot     ⇒ Option(plot.getRangeAxis.getLabel)
      case plot: FastScatterPlot ⇒ Option(plot.getRangeAxis.getLabel)
      case plot: XYPlot          ⇒ Option(plot.getRangeAxis.getLabel)
      case _                     ⇒ None
    }

    /** Labels the range axis of the underlying plot. */
    def rangeAxisLabel_=(label: String): Unit = plot match {
      case plot: CategoryPlot    ⇒ plot.getRangeAxis.setLabel(label)
      case plot: ContourPlot     ⇒ plot.getRangeAxis.setLabel(label)
      case plot: FastScatterPlot ⇒ plot.getRangeAxis.setLabel(label)
      case plot: XYPlot          ⇒ plot.getRangeAxis.setLabel(label)
      case _ ⇒ throw new UnsupportedOperationException (
        "Range axis labels are not supported for the underlying type of plot."
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

      case plot: XYPlot if labels ⇒
        val renderer = plot.getRenderer
        plot.getDomainAxis match {
          case _: DateAxis ⇒
            renderer.setBaseToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance)
          case _ ⇒
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator)
        }

      case plot: XYPlot if ! labels ⇒
        plot.getRenderer.setBaseToolTipGenerator(null)

      case _ ⇒ throw new UnsupportedOperationException (
        "Tool tips are not supported for the underlying type of plot."
      )
    }

  }

}
