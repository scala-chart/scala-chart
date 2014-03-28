package scalax.chart

import org.jfree.chart._
import org.jfree.chart.labels.StandardPieSectionLabelGenerator
import org.jfree.ui.RectangleInsets

import module.Imports._
import module.PieToolTipGenerators._

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
object PieChart extends ChartCompanion[PieChart] with module.PieDatasetConversions with DocMacros {

  override final def fromPeer(jfree: JFreeChart): PieChart = new PieChart {
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
    * @usecase def apply(dataset: PieDataset): PieChart = ???
    *   @inheritdoc
    */
  def apply[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {
    val dataset = data.toDataset

    val plot = new PiePlot(dataset)
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator())
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    val chart = PieChart(plot, title, legend, theme)

    if (tooltips) chart.tooltipGenerator = PieToolTipGenerator.Default

    chart
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
    */
  def threeDimensional[A: ToPieDataset](data: A, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): PieChart = {
    val dataset = data.toDataset

    val plot = new PiePlot3D(dataset)
    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0))

    val chart = PieChart(plot, title, legend, theme)

    if (tooltips) chart.tooltipGenerator = PieToolTipGenerator.Default

    chart
  }

}
