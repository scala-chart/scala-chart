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
import org.jfree.chart.labels._
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

  /** Enriched JFreeChart. */
  implicit class GenericRichChart(val peer: JFreeChart) extends Chart[Plot]
      with Orientable with DomainAxis with RangeAxis
      with Labels[AnyRef]
      with Tooltips[AnyRef] {

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

    override def labelGenerator: Option[AnyRef] = plot match {
      case plot: CategoryPlot    ⇒ Option(plot.getRenderer.getBaseItemLabelGenerator)
      case plot: MultiplePiePlot ⇒ Option(plot.getPieChart.getPlot.asInstanceOf[PiePlot].getLabelGenerator)
      case plot: PiePlot         ⇒ Option(plot.getLabelGenerator)
      case plot: XYPlot          ⇒ Option(plot.getRenderer.getBaseItemLabelGenerator)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) does not support label generation.".format(plot.getPlotType)
      )
    }

    override def labelGenerator_=(generator: Option[AnyRef]): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.getRenderer.setBaseItemLabelGenerator(
        generator.collect({ case gen: CategoryItemLabelGenerator ⇒ gen }).orNull
      )

      case plot: MultiplePiePlot ⇒ plot.getPieChart.getPlot.asInstanceOf[PiePlot].setLabelGenerator(
        generator.collect({ case gen: PieSectionLabelGenerator ⇒ gen }).orNull
      )

      case plot: PiePlot ⇒ plot.setLabelGenerator(
        generator.collect({ case gen: PieSectionLabelGenerator ⇒ gen }).orNull
      )

      case plot: XYPlot ⇒ plot.getRenderer.setBaseItemLabelGenerator(
        generator.collect({ case gen: XYItemLabelGenerator ⇒ gen }).orNull
      )

      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) does not support label generation.".format(plot.getPlotType)
      )
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

    override def tooltipGenerator: Option[AnyRef] = plot match {
      case plot: CategoryPlot    ⇒ Option(plot.getRenderer.getBaseToolTipGenerator)
      case plot: MultiplePiePlot ⇒ Option(plot.getPieChart.getPlot.asInstanceOf[PiePlot].getToolTipGenerator)
      case plot: PiePlot         ⇒ Option(plot.getToolTipGenerator)
      case plot: XYPlot          ⇒ Option(plot.getRenderer.getBaseToolTipGenerator)
      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) does not support tooltip generation.".format(plot.getPlotType)
      )
    }

    override def tooltipGenerator_=(generator: Option[AnyRef]): Unit = plot match {
      case plot: CategoryPlot ⇒ plot.getRenderer.setBaseToolTipGenerator(
        generator.collect({ case gen: CategoryToolTipGenerator ⇒ gen }).orNull
      )

      case plot: MultiplePiePlot ⇒ plot.getPieChart.getPlot.asInstanceOf[PiePlot].setToolTipGenerator(
        generator.collect({ case gen: PieToolTipGenerator ⇒ gen }).orNull
      )

      case plot: PiePlot ⇒ plot.setToolTipGenerator(
        generator.collect({ case gen: PieToolTipGenerator ⇒ gen }).orNull
      )

      case plot: XYPlot ⇒ plot.getRenderer.setBaseToolTipGenerator(
        generator.collect({ case gen: XYToolTipGenerator ⇒ gen }).orNull
      )

      case plot ⇒ throw new UnsupportedOperationException (
        "The underlying plot (%s) does not support tooltip generation.".format(plot.getPlotType)
      )
    }

  }

}
