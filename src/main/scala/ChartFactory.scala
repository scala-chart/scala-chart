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
import org.jfree.data.general._
import org.jfree.data.time._
import org.jfree.data.xy._

import org.jfree.chart.{ JFreeChart, ChartFactory ⇒ JChartFactory }
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.plot._
import org.jfree.chart.plot.PlotOrientation._

import org.jfree.util.TableOrder

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
  * @define labels      whether or not the chart will contain labels
  */
trait ChartFactory {

  /** Creates a new chart that represents numeric `x` and `y` values with an area.
    *
    * If the input dataset is an instance of a `TimePeriodValuesCollection`, `TimeSeriesCollection`
    * or `TimeTableXYDataset` the domain axis will correctly be set to a `DateAxis`.
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
    val chart = JChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, dataset, orientation,
      legend, tooltips, urls)

    dataset match {
      case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _ ⇒
    }

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
    * @param labels      $labels
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
    val chart = JChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, dataset, orientation,
      legend, tooltips, urls)

    chart.getPlot match {
      case plot: CategoryPlot if labels ⇒
        val renderer = plot.getRenderer
        renderer.setBaseItemLabelsVisible(true)
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator)

      case _ ⇒
    }

    chart
  }

  /** Creates a new chart that represents numeric `x` and `y` values with a line.
    *
    * If the input dataset is an instance of a `TimePeriodValuesCollection`, `TimeSeriesCollection`
    * or `TimeTableXYDataset` the domain axis will correctly be set to a `DateAxis`.
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
    val chart = JChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset, orientation,
      legend, tooltips, urls)

    dataset match {
      case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _ ⇒
    }

    chart
  }

  /** Creates a new chart that represents categorized numeric data with a multiple pie.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    * @param urls     $urls
    * @param labels   $labels
    */
  def MultiplePieChart(dataset: CategoryDataset,
                       title: String = "",
                       legend: Boolean = true,
                       tooltips: Boolean = true,
                       urls: Boolean = false,
                       labels: Boolean = true): JFreeChart = {
    val chart = JChartFactory.createMultiplePieChart(title, dataset, TableOrder.BY_COLUMN, legend,
      tooltips, urls)

    chart.getPlot match {
      case plot: MultiplePiePlot ⇒ plot.getPieChart.getPlot match {
        case plot: PiePlot if ! labels ⇒ plot.setLabelGenerator(null)
        case _ ⇒
      }
      case _ ⇒
    }

    chart
  }

  /** Creates a new chart that represents categorized numeric data with a pie.
    *
    * @param dataset          $dataset
    * @param title            $title
    * @param legend           $legend
    * @param tooltips         $tooltips
    * @param urls             $urls
    * @param labels           $labels
    * @param threeDimensional whether or not to generate a 3D pie
    */
  def PieChart(dataset: PieDataset,
               title: String = "",
               legend: Boolean = true,
               tooltips: Boolean = true,
               urls: Boolean = false,
               labels: Boolean = true,
               threeDimensional: Boolean = false): JFreeChart = {
    val chart = if (threeDimensional)
      JChartFactory.createPieChart3D(title, dataset, legend, tooltips, urls)
    else
      JChartFactory.createPieChart(title, dataset, legend, tooltips, urls)

    chart.getPlot match {
      case plot: PiePlot if ! labels ⇒ plot.setLabelGenerator(null)
      case _ ⇒
    }

    chart
  }

  /** Creates a new chart that represents categorized numeric data in a ring.
    *
    * @param dataset          $dataset
    * @param title            $title
    * @param legend           $legend
    * @param tooltips         $tooltips
    * @param urls             $urls
    * @param labels           $labels
    * @param threeDimensional whether or not to generate a 3D pie
    */
  def RingChart(dataset: PieDataset,
                title: String = "",
                legend: Boolean = true,
                tooltips: Boolean = true,
                urls: Boolean = false,
                labels: Boolean = true): JFreeChart = {
    val chart = JChartFactory.createRingChart(title, dataset, legend, tooltips, urls)

    chart.getPlot match {
      case plot: PiePlot if ! labels ⇒ plot.setLabelGenerator(null)
      case _ ⇒
    }

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
    val chart = JChartFactory.createStackedXYAreaChart(title, xAxisLabel, yAxisLabel, dataset,
      orientation, legend, tooltips, urls)

    dataset match {
      case _: TimeTableXYDataset ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
      case _ ⇒
    }

    chart
  }

}
