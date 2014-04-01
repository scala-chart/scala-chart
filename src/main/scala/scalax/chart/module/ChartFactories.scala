package scalax.chart
package module

import scala.collection.GenTraversableOnce

import org.jfree.chart.axis._
import org.jfree.chart.plot.CombinedDomainCategoryPlot
import org.jfree.chart.renderer.category._
import org.jfree.chart.renderer.xy._
import org.jfree.util.SortOrder

/** $ChartFactoriesInfo */
object ChartFactories extends ChartFactories

/** $ChartFactoriesInfo
  *
  * @define ChartFactoriesShortInfo [[ChartFactories]] contains all high-level factories to
  * conveniently create charts.
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = XYLineChart(data)
  * }}}
  *
  * @define ChartFactoriesInfo '' '' $ChartFactoriesShortInfo
  *
  * == Creating Charts ==
  *
  * The only argument needed to create a chart is a dataset:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
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
  * val chart = XYLineChart(dataset, legend = false)
  * }}}
  *
  * == Creating Charts with Themes ==
  *
  * The default theme used is the JFreeChart theme. To apply a different theme to the charts created
  * by the factories, simply define an implicit chart theme in scope, e.g. the darkness theme from
  * JFreeChart:
  *
  * {{{
  * implicit val theme = org.jfree.chart.StandardChartTheme.createDarknessTheme
  * }}}
  */
trait ChartFactories
    extends BoxAndWhiskerChartFactories
    with PieChartFactories
    with DatasetConversions
    with DocMacros {

  // -----------------------------------------------------------------------------------------------
  // some small helpers
  // -----------------------------------------------------------------------------------------------

  private def needsDateAxis(dataset: XYDataset): Boolean = dataset match {
    case _: TimePeriodValuesCollection ⇒ true
    case _: TimeSeriesCollection       ⇒ true
    case _: TimeTableXYDataset         ⇒ true
    case _                             ⇒ false
  }

  private def XYDomainAxis(dateAxis: Boolean) = if (dateAxis) {
    new DateAxis()
  } else {
    val axis = new NumberAxis()
    axis.setAutoRangeIncludesZero(false)
    axis
  }

  // -----------------------------------------------------------------------------------------------
  // factories
  // -----------------------------------------------------------------------------------------------

  /** Factory for area charts. */
  object AreaChart {

    /** Creates a new chart that represents categorized numeric data with an area.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      domainAxis.setCategoryMargin(0.0)

      val rangeAxis = new NumberAxis()

      val renderer = new AreaRenderer()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric data with stacked areas.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def stacked(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToCategoryDataset](data: A,
                title: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true)
               (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      domainAxis.setCategoryMargin(0.0)

      val rangeAxis = new NumberAxis()

      val renderer = new StackedAreaRenderer()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      CategoryChart(plot, title, legend, theme)
    }

  }

  /** Factory for bar charts. */
  object BarChart {

    /** Creates a new chart that represents categorized numeric data with bars.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new BarRenderer()

      import org.jfree.chart.labels.{ ItemLabelAnchor, ItemLabelPosition }
      import org.jfree.ui.TextAnchor

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

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric data with stacked bars.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def stacked(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToCategoryDataset](data: A,
                title: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true)
               (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new StackedBarRenderer()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def threeDimensional(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensional[A: ToCategoryDataset](data: A,
                         title: String = "",
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true)
                        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new BarRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      if (orientation == Orientation.Horizontal) {
        plot.setRowRenderingOrder(SortOrder.DESCENDING)
        plot.setColumnRenderingOrder(SortOrder.DESCENDING)
      }
      plot.setForegroundAlpha(0.75f)

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def threeDimensionalStacked(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensionalStacked[A: ToCategoryDataset](data: A,
                                title: String = "",
                                orientation: Orientation = Orientation.Vertical,
                                legend: Boolean = true)
                               (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new StackedBarRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      if (orientation == Orientation.Horizontal) plot.setColumnRenderingOrder(SortOrder.DESCENDING)

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric data with bars. The keys of the
      * given collection will become the range axis label of the respective plot.
      *
      * {{{
      * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
      * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
      * val data = Map("plot a" -> d1, "plot b" -> d2)
      * val chart = BarChart.combinedDomain(data)
      * }}}
      *
      * @param data $data
      * @param title $title
      * @param legend $legend
      * @param theme $theme
      *
      * @usecase def combinedDomain(data: Map[String,CategoryDataset]): CategoryChart = ???
      *   @inheritdoc
      */
    def combinedDomain[A: ToCategoryDataset](data: GenTraversableOnce[(Comparable[_],A)],
              title: String = "",
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      import RichPlot.{ CategoryPlot => _, _ }

      def CategoryPlotOf(catdata: (Comparable[_],A)): CategoryPlot = {
        val category = catdata._1.toString
        val data = catdata._2
        val plot = BarChart(data).plot
        plot.range.axis.label = category
        plot
      }

      val plots = data.aggregate(Vector[CategoryPlot]())(_ :+ CategoryPlotOf(_), _ ++ _)

      val domainAxis = new CategoryAxis()

      val combinedPlot = plots.foldLeft(new CombinedDomainCategoryPlot(domainAxis)) { (combined,single) =>
        combined add single
        combined
      }

      CategoryChart(combinedPlot, title, legend, theme)
    }

  }

  /** Factory for line charts. */
  object LineChart {

    /** Creates a new chart that represents categorized numeric values with a line.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new LineAndShapeRenderer(true, false)

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      CategoryChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents categorized numeric values with three dimensional a
      * line.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def threeDimensional(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensional[A: ToCategoryDataset](data: A,
                         title: String = "",
                         orientation: Orientation = Orientation.Vertical,
                         legend: Boolean = true)
                        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new LineRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      CategoryChart(plot, title, legend, theme)
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
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToXYDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new XYAreaRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setForegroundAlpha(0.5f)

      XYChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents multiple numeric `x` and `y` values with stacked areas.
      *
      * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
      * be set to a `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def stacked(data: TableXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToTableXYDataset](data: A,
                title: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true)
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToTableXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      domainAxis.setLowerMargin(0.0)
      domainAxis.setUpperMargin(0.0)

      val rangeAxis = new NumberAxis()

      val renderer = new StackedXYAreaRenderer2()
      renderer.setOutline(true)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setRangeAxis(rangeAxis)

      XYChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents multiple numeric `x` and `y` values with a stepped area.
      *
      * If the input dataset is an instance of a `TimePeriodValuesCollection`,
      * `TimeSeriesCollection` or `TimeTableXYDataset` the domain axis will correctly be set to a
      * `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def stepped(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stepped[A: ToXYDataset](data: A,
                title: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true)
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new XYStepAreaRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)
      plot.setDomainCrosshairVisible(false)
      plot.setRangeCrosshairVisible(false)

      XYChart(plot, title, legend, theme)
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
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: IntervalXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToIntervalXYDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToIntervalXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new XYBarRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      XYChart(plot, title, legend, theme)
    }

    /** Creates a new chart that represents numeric `x` (intervals) and `y` values with a line.
      *
      * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
      * be set to a `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def stacked(data: TableXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToTableXYDataset](data: A,
                title: String = "",
                orientation: Orientation = Orientation.Vertical,
                legend: Boolean = true)
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToTableXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new StackedXYBarRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      XYChart(plot, title, legend, theme)
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
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: IntervalXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToIntervalXYDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToIntervalXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new DeviationRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      XYChart(plot, title, legend, theme)
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
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def apply(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToXYDataset](data: A,
              title: String = "",
              orientation: Orientation = Orientation.Vertical,
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val dateAxis = needsDateAxis(dataset)

      val domainAxis = XYDomainAxis(dateAxis)
      val rangeAxis = new NumberAxis()

      val renderer = new XYLineAndShapeRenderer(true, false)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setOrientation(orientation)

      XYChart(plot, title, legend, theme)
    }

  }

}
