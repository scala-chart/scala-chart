package scalax.chart

import org.jfree.chart._
import org.jfree.chart.labels._
import org.jfree.ui._

import Imports._

/** Represents categorized numeric data with a pie. */
trait PieChart extends Chart[PiePlot] with PieChartLike[PiePlot] {
  override def plot: PiePlot = peer.getPlot.asInstanceOf[PiePlot]
}

/** Factory for ${chart}s.
  *
  * @define chart pie chart
  * @define Chart PieChart
  */
object PieChart extends ChartCompanion[PiePlot,PieChart] with DocMacros {

  override final def fromPeer(jfree: JFreeChart): PieChart = new PieChart {
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
    * @usecase def apply(dataset: PieDataset): PieChart = ???
    *   @inheritdoc
    *
    * @usecase def apply(dataset: PieDataset, title: String, legend: Boolean, tooltips: Boolean): PieChart = ???
    *   @inheritdoc
    */
  def apply(dataset: PieDataset, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {

    val plot = new PiePlot(dataset)
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
    if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

    PieChart(plot, title, legend, theme)
  }

  /** Creates a new $chart with a three dimensional visualization.
    *
    * @param dataset  $data
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    * @param theme    $theme
    *
    * @usecase def threeDimensional(dataset: PieDataset): PieChart = ???
    *   @inheritdoc
    *
    * @usecase def threeDimensional(dataset: PieDataset, title: String, legend: Boolean, tooltips: Boolean): PieChart = ???
    *   @inheritdoc
    */
  def threeDimensional(dataset: PieDataset, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {

    val plot = new PiePlot3D(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))
    if (tooltips) plot.setToolTipGenerator(new StandardPieToolTipGenerator())

    PieChart(plot, title, legend, theme)
  }

}
