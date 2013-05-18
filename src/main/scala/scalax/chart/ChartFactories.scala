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

import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.ChartFactory._
import org.jfree.chart.StandardChartTheme.createJFreeTheme
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
  * == Usage ==
  *
  * The only argument needed to create a chart is a dataset:
  *
  * {{{
  * val data = Seq((0,0),(1,1),(2,2))
  * val dataset = data.toXYSeriesCollection("some data")
  * }}}
  *
  * The factories make heavy use of default arguments, so you have to type as less as possible:
  *
  * {{{
  * val chart = XYLineChart(dataset)
  * }}}
  *
  * For better readability of your own code, you should name all other arguments:
  *
  * {{{
  * val chart = XYLineChart(dataset, legend = false, domainAxisLabel = "some description")
  * }}}
  *
  * == Chart Themes ==
  *
  * The default theme used is the JFreeChart theme. To apply a different theme to the charts created
  * by the factories, simply define an implicit chart theme in scope, e.g. the darkness theme from
  * JFreeChart:
  *
  * {{{
  * implicit val theme = org.jfree.chart.StandardChartTheme.createDarknessTheme
  * }}}
  *
  * @define dataset         the data the chart will visualize
  * @define domainAxisLabel the label for the domain axis
  * @define legend          whether or not the chart will contain a legend
  * @define orientation     the orientation of the chart
  * @define rangeAxisLabel  the label for the range axis
  * @define theme           the theme to apply to the chart
  * @define title           the title of the chart
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
      * @param theme           $theme
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def stacked(dataset: CategoryDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false)
               (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createStackedAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createBarChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation, legend,
        tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def stacked(dataset: CategoryDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false)
               (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createStackedBarChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         domainAxisLabel: String = "",
                         rangeAxisLabel: String = "",
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true,
                         tooltips: Boolean = false)
                        (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def threeDimensionalStacked(dataset: CategoryDataset,
                                title: String = "",
                                domainAxisLabel: String = "",
                                rangeAxisLabel: String = "",
                                orientation: Orientation = Orientation.Vertical,
                                legend: Boolean = true,
                                tooltips: Boolean = false)
                               (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createStackedBarChart3D(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: BoxAndWhiskerCategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = true)
             (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createBoxAndWhiskerChart(title, domainAxisLabel, rangeAxisLabel, dataset, legend)

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         domainAxisLabel: String = "",
                         rangeAxisLabel: String = "",
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true,
                         tooltips: Boolean = false)
                        (implicit theme: ChartTheme = createJFreeTheme): CategoryChart = {
      val chart = createLineChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      theme(chart)

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
      * @param theme    $theme
      */
    def apply(dataset: CategoryDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true)
             (implicit theme: ChartTheme = createJFreeTheme): MultiplePieChart = {
      val chart = createMultiplePieChart(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)

      theme(chart)

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
      * @param theme    $theme
      */
    def threeDimensional(dataset: CategoryDataset,
                         title: String = "",
                         legend: Boolean = true,
                         tooltips: Boolean = true)
                        (implicit theme: ChartTheme = createJFreeTheme): MultiplePieChart = {
      val chart = createMultiplePieChart3D(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)

      theme(chart)

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
      * @param theme    $theme
      */
    def apply(dataset: PieDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true)
             (implicit theme: ChartTheme = createJFreeTheme): PieChart = {
      val chart = createPieChart(title, dataset, legend, tooltips, false)

      theme(chart)

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
      * @param theme    $theme
      */
    def threeDimensional(dataset: PieDataset,
                         title: String = "",
                         legend: Boolean = true,
                         tooltips: Boolean = true)
                        (implicit theme: ChartTheme = createJFreeTheme): PieChart = {
      val chart = createPieChart3D(title, dataset, legend, tooltips, false)

      theme(chart)

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
      * @param theme    $theme
      */
    def apply(dataset: PieDataset,
              title: String = "",
              legend: Boolean = true,
              tooltips: Boolean = true)
             (implicit theme: ChartTheme = createJFreeTheme): RingChart = {
      val chart = createRingChart(title, dataset, legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: XYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val chart = createXYAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      theme(chart)

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
      * @param theme           $theme
      */
    def stacked(dataset: TableXYDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false)
               (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val chart = createStackedXYAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      dataset match {
        case _: TimeTableXYDataset ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      theme(chart)

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
      * @param theme           $theme
      */
    def stepped(dataset: XYDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false)
               (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val chart = createXYStepAreaChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: IntervalXYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val dateAxis = dataset match {
        case _: TimePeriodValuesCollection ⇒ true
        case _: TimeSeriesCollection       ⇒ true
        case _: TimeTableXYDataset         ⇒ true
        case _                             ⇒ false
      }

      val chart = createXYBarChart(title, domainAxisLabel, dateAxis, rangeAxisLabel, dataset,
        orientation, legend, tooltips, false)

      theme(chart)

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
      * @param theme           $theme
      */
    def stacked(dataset: IntervalXYDataset with TableXYDataset,
                title: String = "",
                domainAxisLabel: String = "",
                rangeAxisLabel: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true,
                tooltips: Boolean = false)
               (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
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

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: BoxAndWhiskerXYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val chart = createBoxAndWhiskerChart(title, domainAxisLabel, rangeAxisLabel, dataset, legend)

      theme(chart)

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
      * @param theme           $theme
      */
    def apply(dataset: XYDataset,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true,
              tooltips: Boolean = false)
             (implicit theme: ChartTheme = createJFreeTheme): XYChart = {
      val chart = createXYLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
        legend, tooltips, false)

      dataset match {
        case _: TimePeriodValuesCollection ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeSeriesCollection       ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _: TimeTableXYDataset         ⇒ chart.getXYPlot.setDomainAxis(new DateAxis)
        case _ ⇒
      }

      theme(chart)

      new XYChart {
        override val peer = chart
      }
    }

  }

}
