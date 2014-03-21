package scalax.chart

import scala.swing.Orientable

import module.Imports._

/** Represents categorized numeric data. These charts have a domain axis that consists of the
  * categories and a numeric range axis.
  */
abstract class CategoryChart protected () extends Chart with Orientable
    with Labels[CategoryItemLabelGenerator]
    with Tooltips[CategoryToolTipGenerator] {

  type Plot = CategoryPlot

  override def plot: CategoryPlot = peer.getCategoryPlot

  override def labelGenerator: Option[CategoryItemLabelGenerator] = Option (
    plot.getRenderer.getBaseItemLabelGenerator
  ) map { _.generateLabel _ }

  override def labelGenerator_=(generator: Option[CategoryItemLabelGenerator]): Unit = {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(generator.map( gen ⇒
      new org.jfree.chart.labels.CategoryItemLabelGenerator {
        override def generateColumnLabel(dataset: CategoryDataset, col: Int): String = ""
        override def generateLabel(dataset: CategoryDataset, row: Int, col: Int): String =
          gen(dataset, row, col)
        override def generateRowLabel(dataset: CategoryDataset, row: Int): String = ""
      }
    ).orNull)
  }

  override def orientation: Orientation = plot.getOrientation
  override def orientation_=(orientation: Orientation) {
    plot.setOrientation(orientation)
  }

  override def tooltipGenerator: Option[CategoryToolTipGenerator] = Option (
    plot.getRenderer.getBaseToolTipGenerator
  ) map { _.generateToolTip _ }

  override def tooltipGenerator_=(generator: Option[CategoryToolTipGenerator]): Unit = {
    plot.getRenderer.setBaseToolTipGenerator(generator.map( ttg ⇒
      new org.jfree.chart.labels.CategoryToolTipGenerator {
        override def generateToolTip(dataset: CategoryDataset, row: Int, col: Int): String =
          ttg(dataset, row, col)
      }
    ).orNull)
  }

}

/** Low-level factory for ${chart}s.
  *
  * @define chart category chart
  * @define Chart CategoryChart
  */
object CategoryChart extends ChartCompanion[CategoryChart] {
  override final def fromPeer(jfree: JFreeChart): CategoryChart = new CategoryChart {
    override final lazy val peer = jfree
  }
}
