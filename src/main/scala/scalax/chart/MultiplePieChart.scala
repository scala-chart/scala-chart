package scalax.chart

import org.jfree.chart._
import org.jfree.chart.labels._
import org.jfree.chart.plot.MultiplePiePlot
import org.jfree.chart.title.TextTitle
import org.jfree.ui._
import org.jfree.util._

import Imports._

/** Represents categorized numeric data with multiple pies. */
abstract class MultiplePieChart protected () extends Chart[MultiplePiePlot]
    with Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  def underlying: PieChart = {
    val u = plot.getPieChart
    new PieChart {
      override val peer = u
    }
  }

  override def plot: MultiplePiePlot = peer.getPlot.asInstanceOf[MultiplePiePlot]

  override def labelGenerator: Option[PieSectionLabelGenerator] = underlying.labelGenerator
  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]) {
    underlying.labelGenerator = generator
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = underlying.tooltipGenerator
  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]) {
    underlying.tooltipGenerator = generator
  }
}

/** Factory for ${chart}s.
  *
  * @define chart multiple pie chart
  * @define Chart MultiplePieChart
  */
object MultiplePieChart extends ChartCompanion[MultiplePiePlot,MultiplePieChart] with CategoryDatasetConversions {

  override final def fromPeer(jfree: JFreeChart): MultiplePieChart = new MultiplePieChart {
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
    * @usecase def apply(dataset: CategoryDataset): MultiplePieChart = ???
    *   @inheritdoc
    *
    * @usecase def apply(dataset: CategoryDataset, title: String, legend: Boolean, tooltips: Boolean): MultiplePieChart = ???
    *   @inheritdoc
    */
  def apply[A: ToCategoryDataset](data: A, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): MultiplePieChart = {
    val dataset = data.toDataset

    val plot = new MultiplePiePlot(dataset)
    plot.setDataExtractOrder(TableOrder.BY_COLUMN)
    plot.setBackgroundPaint(null)
    plot.setOutlineStroke(null)

    if (tooltips) {
      val pp = plot.getPieChart.getPlot.asInstanceOf[PiePlot]
      pp.setToolTipGenerator(new StandardPieToolTipGenerator())
    }

    MultiplePieChart(plot, title, legend, theme)
  }

  /** Creates a new $chart with three dimensional visualization.
    *
    * @param dataset  $data
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    * @param theme    $theme
    *
    * @usecase def threeDimensional(dataset: CategoryDataset): MultiplePieChart = ???
    *   @inheritdoc
    *
    * @usecase def threeDimensional(dataset: CategoryDataset, title: String, legend: Boolean, tooltips: Boolean): MultiplePieChart = ???
    *   @inheritdoc
    */
  def threeDimensional[A: ToCategoryDataset](data: A, title: String = "", legend: Boolean = true, tooltips: Boolean = true)
    (implicit theme: ChartTheme = StandardChartTheme.createJFreeTheme): MultiplePieChart = {
    val dataset = data.toDataset

    val plot = new MultiplePiePlot(dataset)
    plot.setDataExtractOrder(TableOrder.BY_COLUMN)
    plot.setBackgroundPaint(null)
    plot.setOutlineStroke(null)

    val piePlot = new PiePlot3D()
    if (tooltips) piePlot.setToolTipGenerator(new StandardPieToolTipGenerator())

    val pieChart = new JFreeChart(piePlot)
    pieChart.setTitle(new TextTitle("dummy title for setting edge"))
    pieChart.getTitle.setPosition(RectangleEdge.BOTTOM)
    pieChart.removeLegend()
    plot.setPieChart(pieChart)

    MultiplePieChart(plot, title, legend, theme)
  }

}
