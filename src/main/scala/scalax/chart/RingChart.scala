package scalax.chart

import org.jfree.chart._
import org.jfree.chart.labels._
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
    * @param dataset  $data
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    * @param theme    $theme
    *
    * @usecase def apply(dataset: PieDataset): RingChart = ???
    *   @inheritdoc
    */
  def apply[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): RingChart = {
    val dataset = data.toDataset

    val plot = new RingPlot(dataset)
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
    if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

    RingChart(plot, title, legend, theme)
  }

}
