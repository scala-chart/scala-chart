package scalax.chart

import org.jfree.ui.RectangleInsets

import module.PieDatasetConversions._

/** Represents categorized numeric data with a pie. */
abstract class PieChart protected () extends Chart with PieChartLike {
  type Plot = PiePlot
  override def plot: PiePlot = peer.getPlot.asInstanceOf[PiePlot]
}

/** Factory for ${chart}s.
  *
  * @define chart pie chart
  * @define Chart PieChart
  */
object PieChart extends ChartCompanion[PieChart] {

  override final def fromPeer(jfree: JFreeChart): PieChart = {
    require(jfree.getPlot.isInstanceOf[Plot], "Illegal peer plot type.")

    new PieChart {
      override final lazy val peer = jfree
    }
  }

  /** Creates a new $chart.
    *
    * @param data   $data
    * @param title  $title
    * @param legend $legend
    * @param theme  $theme
    *
    * @usecase def apply(data: PieDataset): PieChart = ???
    *   @inheritdoc
    */
  def apply[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true)
    (implicit theme: ChartTheme = ChartTheme.Default): PieChart = {
    val dataset = ToPieDataset[A].convert(data)

    val plot = new PiePlot(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    PieChart(plot, title, legend, theme)
  }

  /** Creates a new $chart with a three dimensional visualization.
    *
    * @param data   $data
    * @param title  $title
    * @param legend $legend
    * @param theme  $theme
    *
    * @usecase def threeDimensional(data: PieDataset): PieChart = ???
    *   @inheritdoc
    */
  def threeDimensional[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true)
    (implicit theme: ChartTheme = ChartTheme.Default): PieChart = {
    val dataset = ToPieDataset[A].convert(data)

    val plot = new PiePlot3D(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    PieChart(plot, title, legend, theme)
  }

}
