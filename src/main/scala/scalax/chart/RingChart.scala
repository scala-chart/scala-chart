package scalax.chart

import org.jfree.ui.RectangleInsets

import module.PieDatasetConversions._

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
object RingChart extends ChartCompanion[RingChart] {

  override final def fromPeer(jfree: JFreeChart): RingChart = {
    require(jfree.getPlot.isInstanceOf[Plot], "Illegal peer plot type.")

    new RingChart {
      override final lazy val peer = jfree
    }
  }

  /** Creates a new $chart.
    *
    * @param data  $data
    * @param theme $theme
    *
    * @usecase def apply(data: PieDataset): RingChart = ???
    *   @inheritdoc
    */
  def apply[A: ToPieDataset](data: A)
    (implicit theme: ChartTheme = ChartTheme.Default): RingChart = {
    val dataset = ToPieDataset[A].convert(data)

    val plot = new RingPlot(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    RingChart(plot, title = "", legend = true)
  }

}
