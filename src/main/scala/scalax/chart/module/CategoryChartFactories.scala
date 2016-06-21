package scalax.chart
package module

import scala.collection.GenTraversableOnce

import org.jfree.chart.axis._
import org.jfree.chart.plot._
import org.jfree.chart.renderer.category._
import org.jfree.util.SortOrder

/** $CategoryChartFactoriesInfo */
object CategoryChartFactories extends CategoryChartFactories

/** $CategoryChartFactoriesInfo
  *
  * @define CategoryChartFactoriesInfo [[CategoryChartFactories]] contains all high-level
  * factories to conveniently create charts based on category datasets.
  */
trait CategoryChartFactories extends DatasetConversions with DocMacros {

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
        plot.range.axis.label.text = category
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

    /** Creates a new chart that represents categorized numeric values with shapes instead of lines.
      *
      * @param data        $data
      * @param title       $title
      * @param orientation $orientation
      * @param legend      $legend
      * @param theme       $theme
      *
      * @usecase def shapes(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def shapes[A: ToCategoryDataset](data: A,
               title: String = "",
               orientation: Orientation = Orientation.Vertical,
               legend: Boolean = true)
              (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new LineAndShapeRenderer(false, true)

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

}
