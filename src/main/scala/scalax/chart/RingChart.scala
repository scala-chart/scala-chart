package scalax.chart

import org.jfree.ui.RectangleInsets

import module.Imports._

/** Represents categorized numeric data with a ring. */
abstract class RingChart protected () extends Chart with PieChartLike {
  type Plot = RingPlot
  override def plot: RingPlot = peer.getPlot.asInstanceOf[RingPlot]
}

/** Factory for ${chart}s.
  *
  * @define chart ring chart
  * @define Chart RingChart
  */
object RingChart extends ChartCompanion[RingChart] with module.PieDatasetConversions with DocMacros {

  override final def fromPeer(jfree: JFreeChart): RingChart = new RingChart {
    override final lazy val peer = jfree
  }

  /** Creates a new $chart.
    *
    * @param data   $data
    * @param title  $title
    * @param legend $legend
    * @param theme  $theme
    *
    * @usecase def apply(data: PieDataset): RingChart = ???
    *   @inheritdoc
    */
  def apply[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true)
    (implicit theme: ChartTheme = ChartTheme.Default): RingChart = {
    val dataset = data.toDataset

    val plot = new RingPlot(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    RingChart(plot, title, legend, theme)
  }

}
