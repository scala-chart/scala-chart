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

import org.jfree.chart.ChartFactory._
import org.jfree.chart.axis.DateAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.time._
import org.jfree.data.xy._

/** Factory for numeric area charts.
  *
  * @define dataset         the data the chart will visualize
  * @define title           the title of the chart
  * @define domainAxisLabel the label for the domain axis
  * @define rangeAxisLabel  the label for the range axis
  * @define orientation     the orientation of the chart
  * @define legend          whether or not the chart will contain a legend
  * @define tooltips        whether or not tooltips will be generated
  */
object XYAreaChart {

  /** Creates a new chart that represents numeric `x` and `y` values with an area.
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
            orientation: PlotOrientation = PlotOrientation.VERTICAL,
            legend: Boolean = true,
            tooltips: Boolean = false): XYChart = {

    val chart = createXYAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
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

  /** Creates a new chart that represents multiple numeric `x` and `y` values with stacked areas.
    *
    * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
    * be set to a `DateAxis`.
    *
    * @param dataset         $dataset
    * @param title           $title
    * @param domainAxisLabel $domainAxisLabel
    * @param rangeAxisLabel  $rangeAxisLabel
    * @param orientation     $orientation
    * @param legend          $legend
    * @param tooltips        $tooltips
    */
  def stacked(dataset: TableXYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): XYChart = {

    val chart = createStackedXYAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset,
      orientation, legend, tooltips, false)

    dataset match {
      case _: TimeTableXYDataset ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _ ⇒
    }

    new XYChart {
      override val peer = chart
    }
  }

  /** Creates a new chart that represents multiple numeric `x` and `y` values with a stepped area.
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
  def stepped(dataset: XYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): XYChart = {

    val chart = createXYStepAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
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
