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
import org.jfree.chart.plot._

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
  * val chart = XYLineChart(data)
  * }}}
  *
  * The other arguments have defaults and can be conveniently overridden with named arguments:
  *
  * {{{
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = XYLineChart(data, legend = false, domainAxisLabel = "some description")
  * }}}
  *
  * @define dataset         the data the chart will visualize
  * @define title           the title of the chart
  * @define domainAxisLabel the label for the domain axis
  * @define rangeAxisLabel  the label for the range axis
  * @define orientation     the orientation of the chart
  * @define legend          whether or not the chart will contain a legend
  * @define tooltips        whether or not tooltips will be generated
  */
trait ChartFactory {

  /** Factory methods for area charts. */
  object AreaChart {

    /** Creates a new chart that represents categorized numeric data with an area.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): JFreeChart =
      JChartFactory.createAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

    /** Creates a new chart that represents categorized numeric data with stacked areas.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def stacked(dataset: CategoryDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: PlotOrientation = PlotOrientation.VERTICAL,
                legend: Boolean = true,
                tooltips: Boolean = false): JFreeChart =
      JChartFactory.createStackedAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

  }

  /** Factory methods for bar charts. */
  object BarChart {

    /** Creates a new chart that represents categorized numeric data with bars.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): JFreeChart =
      JChartFactory.createBarChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

    /** Creates a new chart that represents categorized numeric data with stacked bars.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def stacked(dataset: CategoryDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: PlotOrientation = PlotOrientation.VERTICAL,
                legend: Boolean = true,
                tooltips: Boolean = false): JFreeChart =
      JChartFactory.createStackedBarChart(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         domainAxisLabel: String = "",
                         rangeAxisLabel: String = "",
                         orientation: PlotOrientation = PlotOrientation.VERTICAL,
                         legend: Boolean = true,
                         tooltips: Boolean = false): JFreeChart =
      JChartFactory.createBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def threeDimensionalStacked(dataset: CategoryDataset,
                                title: String = "",
                                domainAxisLabel: String = "",
                                rangeAxisLabel: String = "",
                                orientation: PlotOrientation = PlotOrientation.VERTICAL,
                                legend: Boolean = true,
                                tooltips: Boolean = false): JFreeChart =
      JChartFactory.createStackedBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

  }

  /** Factory methods for line charts. */
  object LineChart {

    /** Creates a new chart that represents categorized numeric values with a line.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): JFreeChart =
      JChartFactory.createLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

    /** Creates a new chart that represents categorized numeric values with three dimensional a
      * line.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param orientation     $orientation
      * @param legend          $legend
      * @param tooltips        $tooltips
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         domainAxisLabel: String = "",
                         rangeAxisLabel: String = "",
                         orientation: PlotOrientation = PlotOrientation.VERTICAL,
                         legend: Boolean = true,
                         tooltips: Boolean = false): JFreeChart =
      JChartFactory.createLineChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

  }

  /** Factory methods for area charts. */
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
              tooltips: Boolean = false): JFreeChart = {

      val chart = JChartFactory.createXYAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      chart
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
                tooltips: Boolean = false): JFreeChart = {

      val chart = JChartFactory.createStackedXYAreaChart(title, domainAxisLabel, rangeAxisLabel,
        dataset, orientation, legend, tooltips, false)

      dataset match {
        case _: TimeTableXYDataset ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      chart
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
                tooltips: Boolean = false): JFreeChart = {

      val chart = JChartFactory.createXYStepAreaChart(title, domainAxisLabel, rangeAxisLabel,
        dataset, orientation, legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      chart
    }

  }

  /** Factory methods for bar charts. */
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
              tooltips: Boolean = false): JFreeChart = {

      val dateAxis = dataset match {
        case _: TimePeriodValuesCollection ⇒ true
        case _: TimeSeriesCollection       ⇒ true
        case _: TimeTableXYDataset         ⇒ true
        case _                             ⇒ false
      }

      val chart = JChartFactory.createXYBarChart(title, domainAxisLabel, dateAxis, rangeAxisLabel,
        dataset, orientation, legend, tooltips, false)

      chart
    }

  }

  /** Factory methods for line charts. */
  object XYLineChart {

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
              orientation: PlotOrientation = PlotOrientation.VERTICAL,
              legend: Boolean = true,
              tooltips: Boolean = false): JFreeChart = {

      val chart = JChartFactory.createXYLineChart(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      chart
    }

  }

}
