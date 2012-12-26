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

import org.jfree.chart.ChartFactory.createXYBarChart
import org.jfree.chart.axis.DateAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.time._
import org.jfree.data.xy.IntervalXYDataset

/** Factory for numeric bar charts.
  *
  * @define dataset         the data the chart will visualize
  * @define title           the title of the chart
  * @define domainAxisLabel the label for the domain axis
  * @define rangeAxisLabel  the label for the range axis
  * @define orientation     the orientation of the chart
  * @define legend          whether or not the chart will contain a legend
  * @define tooltips        whether or not tooltips will be generated
  */
object XYBarChart {

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
            orientation: PlotOrientation = PlotOrientation.VERTICAL,
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

}
