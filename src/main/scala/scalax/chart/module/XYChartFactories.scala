package scalax.chart
package module

import org.jfree.chart.axis._
import org.jfree.chart.renderer.xy._

/** $XYChartFactoriesInfo */
object XYChartFactories extends XYChartFactories

/** $XYChartFactoriesInfo
  *
  * @define XYChartFactoriesInfo [[XYChartFactories]] contains all high-level factories to
  * conveniently create xy charts.
  */
trait XYChartFactories extends DatasetConversions with DocMacros {

  // -----------------------------------------------------------------------------------------------
  // some small helpers
  // -----------------------------------------------------------------------------------------------

  private def needsDateAxis(dataset: XYDataset): Boolean = dataset match {
    case _: TimePeriodValuesCollection => true
    case _: TimeSeriesCollection       => true
    case _: TimeTableXYDataset         => true
    case _                             => false
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
      * @param theme       $theme
      *
      * @usecase def apply(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToXYDataset](data: A,
              title: String = "")
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new XYAreaRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setForegroundAlpha(0.5f)

      XYChart(plot, title, legend = true, theme)
    }

    /** Creates a new chart that represents multiple numeric `x` and `y` values with stacked areas.
      *
      * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
      * be set to a `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param theme       $theme
      *
      * @usecase def stacked(data: TableXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToTableXYDataset](data: A,
                title: String = "")
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToTableXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      domainAxis.setLowerMargin(0.0)
      domainAxis.setUpperMargin(0.0)

      val rangeAxis = new NumberAxis()

      val renderer = new StackedXYAreaRenderer2()
      renderer.setOutline(true)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setRangeAxis(rangeAxis)

      XYChart(plot, title, legend = true, theme)
    }

    /** Creates a new chart that represents multiple numeric `x` and `y` values with a stepped area.
      *
      * If the input dataset is an instance of a `TimePeriodValuesCollection`,
      * `TimeSeriesCollection` or `TimeTableXYDataset` the domain axis will correctly be set to a
      * `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param theme       $theme
      *
      * @usecase def stepped(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stepped[A: ToXYDataset](data: A,
                title: String = "")
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new XYStepAreaRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setDomainCrosshairVisible(false)
      plot.setRangeCrosshairVisible(false)

      XYChart(plot, title, legend = true, theme)
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
      * @param theme       $theme
      *
      * @usecase def apply(data: IntervalXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToIntervalXYDataset](data: A,
              title: String = "")
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToIntervalXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new XYBarRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend = true, theme)
    }

    /** Creates a new chart that represents numeric `x` (intervals) and `y` values with a line.
      *
      * If the input dataset is an instance of a `TimeTableXYDataset` the domain axis will correctly
      * be set to a `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param theme       $theme
      *
      * @usecase def stacked(data: TableXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToTableXYDataset](data: A,
                title: String = "")
               (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToTableXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new StackedXYBarRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend = true, theme)
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
      * @param theme       $theme
      *
      * @usecase def apply(data: IntervalXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToIntervalXYDataset](data: A,
              title: String = "")
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToIntervalXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new DeviationRenderer()

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend = true, theme)
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
      * @param theme       $theme
      *
      * @usecase def apply(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToXYDataset](data: A,
              title: String = "")
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new XYLineAndShapeRenderer(true, false)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend = true, theme)
    }

    /** Creates a new chart that represents numeric `x` and `y` values with shapes instead of lines.
      *
      * If the input dataset is an instance of a `TimePeriodValuesCollection`,
      * `TimeSeriesCollection` or `TimeTableXYDataset` the domain axis will correctly be set to a
      * `DateAxis`.
      *
      * @param data        $data
      * @param title       $title
      * @param theme       $theme
      *
      * @usecase def shapes(data: XYDataset): XYChart = ???
      *   @inheritdoc
      */
    def shapes[A: ToXYDataset](data: A,
               title: String = "")
              (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = ToXYDataset[A].convert(data)

      val domainAxis = XYDomainAxis(needsDateAxis(dataset))
      val rangeAxis = new NumberAxis()

      val renderer = new XYLineAndShapeRenderer(false, true)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend = true, theme)
    }

  }

}
