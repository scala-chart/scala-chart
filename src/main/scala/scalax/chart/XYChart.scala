package scalax.chart

import scala.swing.Orientable

import module.Imports._
import module.XYToolTipGenerators._

/** Represents numeric data. */
abstract class XYChart protected () extends Chart with Orientable
    with Labels[XYItemLabelGenerator]
    with Tooltips[XYToolTipGenerator] {

  type Plot = XYPlot

  override def plot: XYPlot = peer.getXYPlot

  override def labelGenerator: Option[XYItemLabelGenerator] = Option (
    plot.getRenderer.getBaseItemLabelGenerator
  ) map { _.generateLabel _ }

  override def labelGenerator_=(generator: Option[XYItemLabelGenerator]): Unit = {
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

  override def tooltipGenerator: Option[XYToolTipGenerator] = for {
    jgenerator <- Option(plot.getRenderer.getBaseToolTipGenerator)
    generator = XYToolTipGenerator.fromPeer(jgenerator)
  } yield generator

  override def tooltipGenerator_=(generator: Option[XYToolTipGenerator]): Unit = {
    plot.getRenderer.setBaseToolTipGenerator(
      generator.map(XYToolTipGenerator.toPeer).orNull
    )
  }
}

/** Low-level factory for ${chart}s.
  *
  * @define chart XY chart
  * @define Chart XYChart
  */
object XYChart extends ChartCompanion[XYChart] {
  override final def fromPeer(jfree: JFreeChart): XYChart = new XYChart {
    override final lazy val peer = jfree
  }
}
