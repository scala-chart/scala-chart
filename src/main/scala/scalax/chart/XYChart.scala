package scalax.chart

import scala.swing.Orientable

import module.Imports._

/** Represents numeric data. */
abstract class XYChart protected () extends Chart[XYPlot] with Orientable with DomainAxis with RangeAxis
    with Labels[XYItemLabelGenerator]
    with Tooltips[XYToolTipGenerator] {

  override def plot: XYPlot = peer.getXYPlot

  override def domainAxisLabel: Option[String] = Option(plot.getDomainAxis.getLabel) filterNot { _ == "" }
  override def domainAxisLabel_=(label: Option[String]) {
    plot.getDomainAxis.setLabel(label.getOrElse(""))
  }

  override def labelGenerator: Option[XYItemLabelGenerator] = Option (
    plot.getRenderer.getBaseItemLabelGenerator
  ) map { _.generateLabel _ }

  override def labelGenerator_=(generator: Option[XYItemLabelGenerator]) {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(generator.map( lg ⇒
      new org.jfree.chart.labels.XYItemLabelGenerator {
        override def generateLabel(dataset: XYDataset, series: Int, item: Int): String =
          lg(dataset, series, item)
      }
    ).orNull)
  }

  override def orientation: Orientation = plot.getOrientation
  override def orientation_=(orientation: Orientation) {
    plot.setOrientation(orientation)
  }

  override def rangeAxisLabel: Option[String] = Option(plot.getRangeAxis.getLabel) filterNot { _ == "" }
  override def rangeAxisLabel_=(label: Option[String]) {
    plot.getRangeAxis.setLabel(label.getOrElse(""))
  }

  override def tooltipGenerator: Option[XYToolTipGenerator] = Option (
    plot.getRenderer.getBaseToolTipGenerator
  ) map { _.generateToolTip _ }

  override def tooltipGenerator_=(generator: Option[XYToolTipGenerator]) {
    plot.getRenderer.setBaseToolTipGenerator(generator.map( ttg ⇒
      new org.jfree.chart.labels.XYToolTipGenerator {
        override def generateToolTip(dataset: XYDataset, series: Int, item: Int): String =
          ttg(dataset, series, item)
      }
    ).orNull)
  }

}

/** Low-level factory for ${chart}s.
  *
  * @define chart XY chart
  * @define Chart XYChart
  */
object XYChart extends ChartCompanion[XYPlot,XYChart] {
  override final def fromPeer(jfree: JFreeChart): XYChart = new XYChart {
    override final val peer = jfree
  }
}
