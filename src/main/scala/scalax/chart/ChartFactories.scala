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

import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.StandardChartTheme
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.renderer.category._
import org.jfree.chart.renderer.xy._
import org.jfree.chart.title.TextTitle
import org.jfree.data.statistics._
import org.jfree.data.time._
import org.jfree.data.xy._
import org.jfree.ui._
import org.jfree.util._

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

  // -----------------------------------------------------------------------------------------------
  // some small helpers
  // -----------------------------------------------------------------------------------------------

  private def needsDateAxis(dataset: XYDataset): Boolean = dataset match {
    case _: TimePeriodValuesCollection ⇒ true
    case _: TimeSeriesCollection       ⇒ true
    case _: TimeTableXYDataset         ⇒ true
    case _                             ⇒ false
  }

  private def xyDomainAxis(label: String, dateAxis: Boolean) = if (dateAxis) {
    new DateAxis(label)
  } else {
    val axis = new NumberAxis(label)
    axis.setAutoRangeIncludesZero(false)
    axis
  }

  private def xyToolTipGenerator(dateAxis: Boolean) = if (dateAxis) {
    StandardXYToolTipGenerator.getTimeSeriesInstance()
  } else {
    new StandardXYToolTipGenerator()
  }

  // -----------------------------------------------------------------------------------------------
  // factories
  // -----------------------------------------------------------------------------------------------

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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      domainAxis.setCategoryMargin(0.0)

      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new AreaRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      domainAxis.setCategoryMargin(0.0)

      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new StackedAreaRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new BarRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())
      val (p1,p2) = if (orientation == Orientation.Horizontal) {
        (new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT),
         new ItemLabelPosition(ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_RIGHT))
      } else {
        (new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER),
         new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER))
      }
      renderer.setBasePositiveItemLabelPosition(p1)
      renderer.setBaseNegativeItemLabelPosition(p2)

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new StackedBarRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
                        (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis3D(domainAxisLabel)
      val rangeAxis = new NumberAxis3D(rangeAxisLabel)

      val renderer = new BarRenderer3D()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      if (orientation == Orientation.Horizontal) {
        plot.setRowRenderingOrder(SortOrder.DESCENDING)
        plot.setColumnRenderingOrder(SortOrder.DESCENDING)
      }
      plot.setForegroundAlpha(0.75f)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
                               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis3D(domainAxisLabel)
      val rangeAxis = new NumberAxis3D(rangeAxisLabel)

      val renderer = new StackedBarRenderer3D()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      if (orientation == Orientation.Horizontal) plot.setColumnRenderingOrder(SortOrder.DESCENDING)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)
      rangeAxis.setAutoRangeIncludesZero(false)

      val renderer = new BoxAndWhiskerRenderer()
      renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new LineAndShapeRenderer(true, false)
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
                        (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): CategoryChart = {

      val domainAxis = new CategoryAxis3D(domainAxisLabel)
      val rangeAxis = new NumberAxis3D(rangeAxisLabel)

      val renderer = new LineRenderer3D()
      if (tooltips) renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): MultiplePieChart = {

      val plot = new MultiplePiePlot(dataset)
      plot.setDataExtractOrder(TableOrder.BY_COLUMN)
      plot.setBackgroundPaint(null)
      plot.setOutlineStroke(null)

      if (tooltips) {
        val pp = plot.getPieChart.getPlot.asInstanceOf[PiePlot]
        pp.setToolTipGenerator(new StandardPieToolTipGenerator())
      }

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
                        (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): MultiplePieChart = {

      val plot = new MultiplePiePlot(dataset)
      plot.setDataExtractOrder(TableOrder.BY_COLUMN)
      plot.setBackgroundPaint(null)
      plot.setOutlineStroke(null)

      val piePlot = new PiePlot3D()
      if (tooltips) piePlot.setToolTipGenerator(new StandardPieToolTipGenerator())

      val pieChart = new JFreeChart(piePlot)
      pieChart.setTitle(new TextTitle("dummy title for setting edge"))
      pieChart.getTitle.setPosition(RectangleEdge.BOTTOM)
      pieChart.removeLegend()
      plot.setPieChart(pieChart)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {

      val plot = new PiePlot(dataset)
      plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
      plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
      if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
                        (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {

      val plot = new PiePlot3D(dataset)
      plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
      if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): RingChart = {

      val plot = new RingPlot(dataset)
      plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
      plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
      if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new XYAreaRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setForegroundAlpha(0.5f)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      domainAxis.setLowerMargin(0.0)
      domainAxis.setUpperMargin(0.0)

      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new StackedXYAreaRenderer2()
      renderer.setOutline(true)
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setRangeAxis(rangeAxis)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new XYStepAreaRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setDomainCrosshairVisible(false)
      plot.setRangeCrosshairVisible(false)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new XYBarRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
               (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new StackedXYBarRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val domainAxis = new DateAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)
      rangeAxis.setAutoRangeIncludesZero(false)

      val renderer = new XYBoxAndWhiskerRenderer(10.0)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
      theme(chart)

      new XYChart {
        override val peer = chart
      }
    }

  }

  /** Factory for numeric deviation charts. */
  object XYDeviationChart {

    /** Creates a new chart that represents numeric `x` and `y` values with a line and a shaded area.
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new DeviationRenderer()
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
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
             (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): XYChart = {

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = xyDomainAxis(domainAxisLabel, dateAxis)
      val rangeAxis = new NumberAxis(rangeAxisLabel)

      val renderer = new XYLineAndShapeRenderer(true, false)
      if (tooltips) renderer.setBaseToolTipGenerator(xyToolTipGenerator(dateAxis))

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      val chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
      theme(chart)

      new XYChart {
        override val peer = chart
      }
    }

  }

}
