package scalax.chart
package module

import scala.collection.GenTraversableOnce

import org.jfree.chart.axis._
import org.jfree.chart.plot._
import org.jfree.chart.renderer.category._

/** $CategoryChartFactoriesInfo */
object CategoryChartFactories extends CategoryChartFactories

/** $CategoryChartFactoriesInfo
  *
  * @define CategoryChartFactoriesInfo [[CategoryChartFactories]] contains all high-level
  * factories to conveniently create charts based on category datasets.
  */
trait CategoryChartFactories extends DatasetConversions with RichPlot with DocMacros {

  /** Factory for area charts. */
  object AreaChart {

    // category axes for area charts need zero margin or else the area would be visually split
    private def DomainAxis() = {
      val axis = new CategoryAxis()
      axis.setCategoryMargin(0.0)
      axis
    }

    private def Renderer(stacked: Boolean) =
      if (stacked)
        new StackedAreaRenderer()
      else
        new AreaRenderer()

    /** Creates a new chart that represents categorized numeric data with areas.
      *
      * @param data    $data
      * @param stacked $stacked
      * @param theme   $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A, stacked: Boolean = false)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)
      val renderer = Renderer(stacked)
      val rangeAxis = new NumberAxis()
      val plot = new CategoryPlot(dataset, DomainAxis(), rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Factories for combined area charts. */
    object combined {

      /** Creates a new chart that represents categorized numeric data with areas.
        *
        * @param data    $data
        * @param stacked $stacked
        * @param theme   $theme
        *
        * @usecase def domain(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def domain[A: ToCategoryDataset](data: Traversable[(String,A)], stacked: Boolean = false)
                (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val renderer = Renderer(stacked)
          val rangeAxis = new NumberAxis()
          val plot = new CategoryPlot(dataset, null, rangeAxis, renderer)
          plot.range.axis.label.text = category
          plot
        }

        val plot = new CombinedDomainCategoryPlot(DomainAxis())

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

      /** Creates a new chart that represents categorized numeric data with areas.
        *
        * @param data    $data
        * @param stacked $stacked
        * @param theme   $theme
        *
        * @usecase def range(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def range[A: ToCategoryDataset](data: Traversable[(String,A)], stacked: Boolean = false)
               (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val renderer = Renderer(stacked)
          val plot = new CategoryPlot(dataset, DomainAxis(), null, renderer)
          plot.domain.axis.label.text = category
          plot
        }

        val rangeAxis = new NumberAxis()
        val plot = new CombinedRangeCategoryPlot(rangeAxis)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }
    }

  }

  /** Factory for bar charts. */
  object BarChart {

    /** Creates a new chart that represents categorized numeric data with bars.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new BarRenderer()

      import org.jfree.chart.labels.{ ItemLabelAnchor, ItemLabelPosition }
      import org.jfree.ui.TextAnchor

      renderer.setBasePositiveItemLabelPosition(
        new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER))
      renderer.setBaseNegativeItemLabelPosition(
        new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER))

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Creates a new chart that represents categorized numeric data with stacked bars.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def stacked(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def stacked[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new StackedBarRenderer()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def threeDimensional(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensional[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new BarRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)
      plot.setForegroundAlpha(0.75f)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Creates a new chart that represents categorized numeric data with three dimensional bars.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def threeDimensionalStacked(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensionalStacked[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new StackedBarRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
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
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def combinedDomain(data: Map[String,CategoryDataset]): CategoryChart = ???
      *   @inheritdoc
      */
    def combinedDomain[A: ToCategoryDataset](data: GenTraversableOnce[(Comparable[_],A)])
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      def CategoryPlotOf(catdata: (Comparable[_],A)): CategoryPlot = {
        val category = catdata._1.toString
        val data = catdata._2
        val plot = BarChart(data).plot
        plot.range.axis.label.text = category
        plot
      }

      val subplots = data.aggregate(Vector[CategoryPlot]())(_ :+ CategoryPlotOf(_), _ ++ _)

      val domainAxis = new CategoryAxis()

      val plot = new CombinedDomainCategoryPlot(domainAxis)

      subplots foreach plot.add

      CategoryChart(plot, title = "", legend = true)
    }

  }

  /** Factory for line charts. */
  object LineChart {

    /** Creates a new chart that represents categorized numeric values with a line.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new LineAndShapeRenderer(true, false)

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Creates a new chart that represents categorized numeric values with shapes instead of lines.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def shapes(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def shapes[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis()
      val rangeAxis = new NumberAxis()

      val renderer = new LineAndShapeRenderer(false, true)

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Creates a new chart that represents categorized numeric values with three dimensional a
      * line.
      *
      * @param data  $data
      * @param theme $theme
      *
      * @usecase def threeDimensional(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def threeDimensional[A: ToCategoryDataset](data: A)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)

      val domainAxis = new CategoryAxis3D()
      val rangeAxis = new NumberAxis3D()

      val renderer = new LineRenderer3D()

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

  }

}
