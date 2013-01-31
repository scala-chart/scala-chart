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

import scala.swing.Orientation

import org.jfree.chart.ChartFactory.createXYLineChart
import org.jfree.chart.axis.DateAxis
import org.jfree.data.time._
import org.jfree.data.xy.XYDataset

/** Factory for numeric line charts. */
object XYLineChart extends ChartFactory {

  /** Creates a new chart that represents numeric `x` and `y` values with a line.
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
  def apply(dataset: XYDataset,
            title: String = "",
            domainAxisLabel: String = "",
            rangeAxisLabel: String = "",
            orientation: Orientation.Value = Orientation.Vertical,
            legend: Boolean = true,
            tooltips: Boolean = false): XYChart = {

    val chart = createXYLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
      legend, tooltips, false)

    dataset match {
      case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _ ⇒
    }

    new XYChart {
      override val peer = chart
    }
  }

}
