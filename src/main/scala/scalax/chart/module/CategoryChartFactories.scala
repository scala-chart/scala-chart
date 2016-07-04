package scalax.chart
package module

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
      val domain = DomainAxis()
      val range = new NumberAxis()
      val renderer = Renderer(stacked)
      val plot = new CategoryPlot(dataset, domain, range, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Factories for combined area charts. */
    object combined {

      /** Creates a new chart that represents categorized numeric data with areas. The keys of the
        * given collection will become the domain axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = AreaChart.combined.domain(data)
        * }}}
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
          val range = new NumberAxis()
          val renderer = Renderer(stacked)
          val plot = new CategoryPlot(dataset, null, range, renderer)
          plot.range.axis.label.text = category
          plot
        }

        val domain = DomainAxis()
        val plot = new CombinedDomainCategoryPlot(domain)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

      /** Creates a new chart that represents categorized numeric data with areas. The keys of the
        * given collection will become the domain axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = AreaChart.combined.range(data)
        * }}}
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
          val domain = DomainAxis()
          val renderer = Renderer(stacked)
          val plot = new CategoryPlot(dataset, domain, null, renderer)
          plot.domain.axis.label.text = category
          plot
        }

        val range = new NumberAxis()
        val plot = new CombinedRangeCategoryPlot(range)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }
    }

  }

  /** Factory for bar charts. */
  object BarChart {

    private def DomainAxis(threeDimensional: Boolean) =
      if (threeDimensional)
        new CategoryAxis3D
      else
        new CategoryAxis

    private def RangeAxis(threeDimensional: Boolean) =
      if (threeDimensional)
        new NumberAxis3D
      else
        new NumberAxis

    private def Renderer(stacked: Boolean, threeDimensional: Boolean) = (stacked, threeDimensional) match {
      case (true,  true ) => new StackedBarRenderer3D
      case (true,  false) => new StackedBarRenderer
      case (false, true ) => new        BarRenderer3D
      case (false, false) => new        BarRenderer
    }

    /** Creates a new chart that represents categorized numeric data with bars.
      *
      * @param data             $data
      * @param stacked          $stacked
      * @param threeDimensional $threeDimensional
      * @param theme            $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A, stacked: Boolean = false, threeDimensional: Boolean = false)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)
      val domain = DomainAxis(threeDimensional)
      val range = RangeAxis(threeDimensional)
      val renderer = Renderer(stacked, threeDimensional)
      val plot = new CategoryPlot(dataset, domain, range, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Factories for combined bar charts. */
    object combined {

      /** Creates a new chart that represents categorized numeric data with bars. The keys of the
        * given collection will become the range axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = BarChart.combined.domain(data)
        * }}}
        *
        * @param data             $data
        * @param stacked          $stacked
        * @param threeDimensional $threeDimensional
        * @param theme            $theme
        *
        * @usecase def domain(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def domain[A: ToCategoryDataset](data: Traversable[(String,A)], stacked: Boolean = false, threeDimensional: Boolean = false)
        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val range = RangeAxis(threeDimensional)
          val renderer = Renderer(stacked, threeDimensional)
          val plot = new CategoryPlot(dataset, null, range, renderer)
          plot.range.axis.label.text = category
          plot
        }

        val domain = DomainAxis(threeDimensional)
        val plot = new CombinedDomainCategoryPlot(domain)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

      /** Creates a new chart that represents categorized numeric data with bars. The keys of the
        * given collection will become the domain axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = BarChart.combined.range(data)
        * }}}
        *
        * @param data             $data
        * @param stacked          $stacked
        * @param threeDimensional $threeDimensional
        * @param theme            $theme
        *
        * @usecase def range(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def range[A: ToCategoryDataset](data: Traversable[(String,A)], stacked: Boolean = false, threeDimensional: Boolean = false)
        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val domain = DomainAxis(threeDimensional)
          val renderer = Renderer(stacked, threeDimensional)
          val plot = new CategoryPlot(dataset, domain, null, renderer)
          plot.domain.axis.label.text = category
          plot
        }

        val range = RangeAxis(threeDimensional)
        val plot = new CombinedRangeCategoryPlot(range)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

    }

  }

  /** Factory for line charts. */
  object LineChart {

    private def DomainAxis(threeDimensional: Boolean) =
      if (threeDimensional)
        new CategoryAxis3D
      else
        new CategoryAxis

    private def RangeAxis(threeDimensional: Boolean) =
      if (threeDimensional)
        new NumberAxis3D
      else
        new NumberAxis

    private def Renderer(threeDimensional: Boolean) =
      if (threeDimensional)
        new LineRenderer3D
      else
        new LineAndShapeRenderer(true, false)

    /** Creates a new chart that represents categorized numeric values with a line.
      *
      * @param data             $data
      * @param threeDimensional $threeDimensional
      * @param theme            $theme
      *
      * @usecase def apply(data: CategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToCategoryDataset](data: A, threeDimensional: Boolean = false)
      (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = ToCategoryDataset[A].convert(data)
      val domain = DomainAxis(threeDimensional)
      val range = RangeAxis(threeDimensional)
      val renderer = Renderer(threeDimensional)
      val plot = new CategoryPlot(dataset, domain, range, renderer)

      CategoryChart(plot, title = "", legend = true)
    }

    /** Factories for combined line charts. */
    object combined {

      /** Creates a new chart that represents categorized numeric data with lines. The keys of the
        * given collection will become the range axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = LineChart.combined.domain(data)
        * }}}
        *
        * @param data             $data
        * @param threeDimensional $threeDimensional
        * @param theme            $theme
        *
        * @usecase def domain(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def domain[A: ToCategoryDataset](data: Traversable[(String,A)], threeDimensional: Boolean = false)
        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val range = RangeAxis(threeDimensional)
          val renderer = Renderer(threeDimensional)
          val plot = new CategoryPlot(dataset, null, range, renderer)
          plot.range.axis.label.text = category
          plot
        }

        val domain = DomainAxis(threeDimensional)
        val plot = new CombinedDomainCategoryPlot(domain)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

      /** Creates a new chart that represents categorized numeric data with lines. The keys of the
        * given collection will become the domain axis label of the respective plot.
        *
        * {{{
        * val d1 = List("series a" -> List("category a" -> 2, "category b" -> 3))
        * val d2 = List("series b" -> List("category a" -> 1, "category b" -> 4))
        * val data = Map("plot a" -> d1, "plot b" -> d2)
        * val chart = LineChart.combined.range(data)
        * }}}
        *
        * @param data             $data
        * @param threeDimensional $threeDimensional
        * @param theme            $theme
        *
        * @usecase def range(data: Map[String,CategoryDataset]): CategoryChart = ???
        *   @inheritdoc
        */
      def range[A: ToCategoryDataset](data: Traversable[(String,A)], threeDimensional: Boolean = false)
        (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

        val subplots = data map { case (category, data) =>
          val dataset = ToCategoryDataset[A].convert(data)
          val domain = DomainAxis(threeDimensional)
          val renderer = Renderer(threeDimensional)
          val plot = new CategoryPlot(dataset, domain, null, renderer)
          plot.domain.axis.label.text = category
          plot
        }

        val range = RangeAxis(threeDimensional)
        val plot = new CombinedRangeCategoryPlot(range)

        subplots foreach plot.add

        CategoryChart(plot, title = "", legend = true)
      }

    }

  }

}
