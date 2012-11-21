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

import org.jfree.data.category._
import org.jfree.data.time._
import org.jfree.data.xy._

import org.jfree.chart.{ JFreeChart, ChartFactory ⇒ JChartFactory }
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.plot._
import org.jfree.chart.plot.PlotOrientation._

/** $ChartFactoryInfo */
object ChartFactory extends ChartFactory

/** $ChartFactoryInfo
  *
  * @define ChartFactoryInfo Contains factory methods to conveniently create charts.
  *
  * The only argument needed to create a chart is the dataset:
  *
  * {{{
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = LineChart(data)
  * }}}
  *
  * The other arguments have defaults and can be conveniently overridden with named arguments:
  *
  * {{{
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = LineChart(data, legend = false, xAxisLabel = "some description")
  * }}}
  *
  * @define dataset     the data the chart will visualize
  * @define title       the title of the chart
  * @define xAxisLabel  the label for the x-axis
  * @define yAxisLabel  the label for the y-axis
  * @define orientation the orientation of the chart
  * @define legend      whether or not the chart will contain a legend
  * @define tooltips    whether or not tooltips will be generated
  * @define urls        whether or not URLs will be generated
  */
trait ChartFactory {

  /** Creates a new chart that represents numeric `x` and `y` values with an area.
    *
    * @param dataset     $dataset
    * @param title       $title
    * @param xAxisLabel  $xAxisLabel
    * @param yAxisLabel  $yAxisLabel
    * @param orientation $orientation
    * @param legend      $legend
    * @param tooltips    $tooltips
    * @param urls        $urls
    */
  def AreaChart(dataset: XYDataset,
                title: String = "",
                xAxisLabel: String = "",
                yAxisLabel: String = "",
                orientation: PlotOrientation = VERTICAL,
                legend: Boolean = true,
                tooltips: Boolean = false,
                urls: Boolean = false): JFreeChart = {
    val chart = JChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimePeriodValuesCollection] ||
        dataset.isInstanceOf[TimeSeriesCollection])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

  /** Creates a new chart that represents categorized numeric data with bars.
    *
    * @param dataset     $dataset
    * @param title       $title
    * @param xAxisLabel  $xAxisLabel
    * @param yAxisLabel  $yAxisLabel
    * @param orientation $orientation
    * @param legend      $legend
    * @param tooltips    $tooltips
    * @param urls        $urls
    * @param labels      whether the top of the bars show the label showing its numeric value
    */
  def BarChart(dataset: CategoryDataset,
               title: String = "",
               xAxisLabel: String = "",
               yAxisLabel: String = "",
               orientation: PlotOrientation = VERTICAL,
               legend: Boolean = true,
               tooltips: Boolean = false,
               urls: Boolean = false,
               labels: Boolean = false): JFreeChart = {
    val chart = JChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (labels) {
      val renderer = chart.getPlot.asInstanceOf[CategoryPlot].getRenderer
      val labelgen = new StandardCategoryItemLabelGenerator

      renderer.setBaseItemLabelsVisible(true)
      renderer.setBaseItemLabelGenerator(labelgen)
    }

    chart
  }

  /** Creates a new chart that represents numeric `x` and `y` values with a line.
    *
    * @param dataset     $dataset
    * @param title       $title
    * @param xAxisLabel  $xAxisLabel
    * @param yAxisLabel  $yAxisLabel
    * @param orientation $orientation
    * @param legend      $legend
    * @param tooltips    $tooltips
    * @param urls        $urls
    */
  def LineChart(dataset: XYDataset,
                title: String = "",
                xAxisLabel: String = "",
                yAxisLabel: String = "",
                orientation: PlotOrientation = VERTICAL,
                legend: Boolean = true,
                tooltips: Boolean = false,
                urls: Boolean = false): JFreeChart = {
    val chart = JChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimePeriodValuesCollection] ||
        dataset.isInstanceOf[TimeSeriesCollection])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

  /** Creates a new chart that represents multiple numeric `x` and `y` values with stacked areas.
    *
    * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
    * be set to a `DateAxis`.
    *
    * @param dataset     $dataset
    * @param title       $title
    * @param xAxisLabel  $xAxisLabel
    * @param yAxisLabel  $yAxisLabel
    * @param orientation $orientation
    * @param legend      $legend
    * @param tooltips    $tooltips
    * @param urls        $urls
    */
  def StackedAreaChart(dataset: TableXYDataset,
                       title: String = "",
                       xAxisLabel: String = "",
                       yAxisLabel: String = "",
                       orientation: PlotOrientation = VERTICAL,
                       legend: Boolean = true,
                       tooltips: Boolean = false,
                       urls: Boolean = false): JFreeChart = {
    val chart = JChartFactory.createStackedXYAreaChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimeTableXYDataset])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

}
