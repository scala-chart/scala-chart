/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012-2013 Christian Krause                                                       *
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

import scala.swing.Orientation

import org.jfree.chart.JFreeChart
import org.jfree.chart.ChartFactory.{ createXYBarChart, getChartTheme }
import org.jfree.chart.axis.{ DateAxis, NumberAxis }
import org.jfree.chart.labels.StandardXYToolTipGenerator
import org.jfree.chart.renderer.xy.StackedXYBarRenderer
import org.jfree.chart.plot.XYPlot
import org.jfree.data.time._
import org.jfree.data.xy._

/** Factory for numeric bar charts. */
object XYBarChart extends ChartFactory {

  /** Creates a new chart that represents numeric `x` (intervals) and `y` values with a line.
    *
    * If the input dataset is an instance of a `TimePeriodValuesCollection`,
    * `TimeSeriesCollection` or `TimeTableXYDataset` the domain axis will correctly be set to a
    * `DateAxis`.
    *
    * @param dataset         $dataset
    * @param title           $title
    * @param domainAxisLabel $domainAxisLabel
    * @param rangeAxisLabel  $rangeAxisLabel
    * @param orientation     $orientation
    * @param legend          $legend
    * @param tooltips        $tooltips
    */
  def apply(dataset: IntervalXYDataset,
            title: String = "",
            domainAxisLabel: String = "",
            rangeAxisLabel: String = "",
            orientation: Orientation.Value = Orientation.Vertical,
            legend: Boolean = true,
            tooltips: Boolean = false): XYChart = {

    val dateAxis = dataset match {
      case _: TimePeriodValuesCollection ⇒ true
      case _: TimeSeriesCollection       ⇒ true
      case _: TimeTableXYDataset         ⇒ true
      case _                             ⇒ false
    }

    val chart = createXYBarChart(title, domainAxisLabel, dateAxis, rangeAxisLabel, dataset,
      orientation, legend, tooltips, false)

    new XYChart {
      override val peer = chart
    }
  }

  /** Creates a new chart that represents numeric `x` (intervals) and `y` values with a line.
    *
    * If the input dataset is an instance of a `TimePeriodValuesCollection`,
    * `TimeSeriesCollection` or `TimeTableXYDataset` the domain axis will correctly be set to a
    * `DateAxis`.
    *
    * @param dataset         $dataset
    * @param title           $title
    * @param domainAxisLabel $domainAxisLabel
    * @param rangeAxisLabel  $rangeAxisLabel
    * @param orientation     $orientation
    * @param legend          $legend
    * @param tooltips        $tooltips
    */
  def stacked(dataset: IntervalXYDataset with TableXYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation.Value = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false): XYChart = {

    val dateAxis = dataset match {
      case _: TimeTableXYDataset ⇒ true
      case _                     ⇒ false
    }

    val domainAxis = if (dateAxis) {
      new DateAxis(domainAxisLabel)
    } else {
      val axis = new NumberAxis(domainAxisLabel)
      axis.setAutoRangeIncludesZero(false)
      axis
    }

    val valueAxis = new NumberAxis(rangeAxisLabel)

    val renderer = new StackedXYBarRenderer()

    if (tooltips) {
      if (dateAxis) {
        renderer.setBaseToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance)
      } else {
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator())
      }
    }

    val plot = new XYPlot(dataset, domainAxis, valueAxis, renderer)
    plot.setOrientation(orientation)

    val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
    getChartTheme()(chart)

    new XYChart {
      override val peer = chart
    }
  }

}
