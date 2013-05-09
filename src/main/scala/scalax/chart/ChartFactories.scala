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

import org.jfree.chart.JFreeChart
import org.jfree.chart.ChartFactory._
import org.jfree.chart.axis._
import org.jfree.chart.labels.StandardXYToolTipGenerator
import org.jfree.chart.renderer.xy.StackedXYBarRenderer
import org.jfree.data.category.CategoryDataset
import org.jfree.data.statistics._
import org.jfree.data.time._
import org.jfree.data.xy._
import org.jfree.util.TableOrder

import Imports._

/** $ChartFactoriesInfo */
object ChartFactories extends ChartFactories

/** $ChartFactoriesInfo
  *
  * @define ChartFactoriesInfo Contains various factories to conveniently create charts.
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
trait ChartFactories {

  /** Factory for area charts. */
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
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false): CategoryChart = {
      val chart = createAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

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
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false): CategoryChart = {
      val chart = createStackedAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

  }

  /** Factory for bar charts. */
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
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false): CategoryChart = {
      val chart = createBarChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation, legend,
        tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

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
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false): CategoryChart = {
      val chart = createStackedBarChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

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
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true,
                         tooltips: Boolean = false): CategoryChart = {
      val chart = createBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

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
                                orientation: Orientation = Orientation.Vertical,
                                legend: Boolean = true,
                                tooltips: Boolean = false): CategoryChart = {
      val chart = createStackedBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

  }

  /** Factory for box and whisker charts. */
  object BoxAndWhiskerChart {

    /** Creates a new box and whisker chart.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param legend          $legend
      */
    def apply(dataset: BoxAndWhiskerCategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = true): CategoryChart = {
      val chart = createBoxAndWhiskerChart(title, domainAxisLabel, rangeAxisLabel, dataset, legend)

      new CategoryChart {
        override val peer = chart
      }
    }

  }

  /** Factory for line charts. */
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
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false): CategoryChart = {
      val chart = createLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

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
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true,
                         tooltips: Boolean = false): CategoryChart = {
      val chart = createLineChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      new CategoryChart {
        override val peer = chart
      }
    }

  }

  /** Factory for multiple pie charts. */
  object MultiplePieChart {

    /** Creates a new multiple pie chart.
      *
      * @param dataset  $dataset
      * @param title    $title
      * @param legend   $legend
      * @param tooltips $tooltips
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true): MultiplePieChart = {
      val chart = createMultiplePieChart(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)
      new MultiplePieChart {
        override val peer = chart
      }
    }

    /** Creates a new multiple pie chart with three dimensional pies.
      *
      * @param dataset  $dataset
      * @param title    $title
      * @param legend   $legend
      * @param tooltips $tooltips
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         legend: Boolean = true,
                         tooltips: Boolean = true): MultiplePieChart = {
      val chart = createMultiplePieChart3D(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)
      new MultiplePieChart {
        override val peer = chart
      }
    }

  }

  /** Factory for pie charts. */
  object PieChart {

    /** Creates a new pie chart.
      *
      * @param dataset  $dataset
      * @param title    $title
      * @param legend   $legend
      * @param tooltips $tooltips
      */
    def apply(dataset: PieDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true): PieChart = {
      val chart = createPieChart(title, dataset, legend, tooltips, false)
      new PieChart {
        override val peer = chart
      }
    }

    /** Creates a new pie chart with a three dimensional pie.
      *
      * @param dataset  $dataset
      * @param title    $title
      * @param legend   $legend
      * @param tooltips $tooltips
      */
    def threeDimensional(dataset: PieDataset,
                         title: String = "",
                         legend: Boolean = true,
                         tooltips: Boolean = true): PieChart = {
      val chart = createPieChart3D(title, dataset, legend, tooltips, false)
      new PieChart {
        override val peer = chart
      }
    }

  }

  /** Factory for ring charts. */
  object RingChart {

    /** Creates a new ring chart.
      *
      * @param dataset  $dataset
      * @param title    $title
      * @param legend   $legend
      * @param tooltips $tooltips
      */
    def apply(dataset: PieDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true): RingChart = {
      val chart = createRingChart(title, dataset, legend, tooltips, false)
      new RingChart {
        override val peer = chart
      }
    }

  }

  /** Factory for numeric area charts. */
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
              orientation: Orientation = Orientation.Vertical,
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
                orientation: Orientation = Orientation.Vertical,
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
                orientation: Orientation = Orientation.Vertical,
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

  /** Factory for numeric bar charts. */
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
              orientation: Orientation = Orientation.Vertical,
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
    def stacked(dataset: IntervalXYDataset with TableXYDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
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

  /** Factory for box and whisker charts. */
  object XYBoxAndWhiskerChart {

    /** Creates a new box and whisker chart.
      *
      * @param dataset         $dataset
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param legend          $legend
      */
    def apply(dataset: BoxAndWhiskerXYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = false): XYChart = {
      val chart = createBoxAndWhiskerChart(title, domainAxisLabel, rangeAxisLabel, dataset, legend)

      new XYChart {
        override val peer = chart
      }
    }

  }

  /** Factory for numeric line charts. */
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
              orientation: Orientation = Orientation.Vertical,
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

}
