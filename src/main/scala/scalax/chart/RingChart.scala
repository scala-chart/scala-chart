package scalax.chart

import org.jfree.chart._
import org.jfree.chart.labels._
import org.jfree.ui._

import Imports._

/** Represents categorized numeric data with a ring. */
trait RingChart extends Chart[RingPlot] with PieChartLike[RingPlot] {
  override def plot: RingPlot = peer.getPlot.asInstanceOf[RingPlot]
}

/** Factory for ${chart}s.
  *
  * @define chart ring chart
  * @define Chart RingChart
  */
object RingChart extends ChartCompanion[RingPlot,RingChart] with DocMacros {

  override final def fromPeer(jfree: JFreeChart): RingChart = new RingChart {
    override final val peer = jfree
  }

  /** Creates a new $chart.
    *
    * @param dataset  $data
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    * @param theme    $theme
    *
    * @usecase def apply(dataset: PieDataset): RingChart
    *   @inheritdoc
    *
    * @usecase def apply(dataset: PieDataset, title: String, legend: Boolean, tooltips: Boolean): RingChart
    *   @inheritdoc
    */
  def apply(dataset: PieDataset, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): RingChart = {

    val plot = new RingPlot(dataset)
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
    if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

    RingChart(plot, title, legend, theme)
  }

}
