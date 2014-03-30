package scalax.chart

import scala.swing.Orientable

import module.Imports._
import module.CategoryLabelGenerators._
import module.CategoryToolTipGenerators._

/** Represents categorized numeric data. These charts have a domain axis that consists of the
  * categories and a numeric range axis.
  */
abstract class CategoryChart protected () extends Chart with Orientable
    with Labels[CategoryLabelGenerator]
    with Tooltips[CategoryToolTipGenerator] {

  type Plot = CategoryPlot

  override def plot: CategoryPlot = peer.getCategoryPlot

  override def labelGenerator: Option[CategoryLabelGenerator] = for {
    jgenerator <- Option(plot.getRenderer.getBaseItemLabelGenerator)
    generator = CategoryLabelGenerator.fromPeer(jgenerator)
  } yield generator

  override def labelGenerator_=(generator: Option[CategoryLabelGenerator]): Unit = {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(
      generator.map(CategoryLabelGenerator.toPeer).orNull
    )
  }

  override def orientation: Orientation = plot.getOrientation
  override def orientation_=(orientation: Orientation) {
    plot.setOrientation(orientation)
  }

  override def tooltipGenerator: Option[CategoryToolTipGenerator] = for {
    jgenerator <- Option(plot.getRenderer.getBaseToolTipGenerator)
    generator = CategoryToolTipGenerator.fromPeer(jgenerator)
  } yield generator

  override def tooltipGenerator_=(generator: Option[CategoryToolTipGenerator]): Unit = {
    plot.getRenderer.setBaseToolTipGenerator(
      generator.map(CategoryToolTipGenerator.toPeer).orNull
    )
  }
}

/** Low-level factory for ${chart}s.
  *
  * @define chart category chart
  * @define Chart CategoryChart
  */
object CategoryChart extends ChartCompanion[CategoryChart] {
  override final def fromPeer(jfree: JFreeChart): CategoryChart = {
    require(jfree.getPlot.isInstanceOf[Plot], "Illegal peer plot type.")

    new CategoryChart {
      override final lazy val peer = jfree
    }
  }
}
