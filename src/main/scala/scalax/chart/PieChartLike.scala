package scalax.chart

import module.Imports._
import module.PieToolTipGenerators._

/** Template trait for pie charts. */
private[chart] trait PieChartLike
    extends Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  self: Chart {
    type Plot <: PiePlot
  } =>

  override def labelGenerator: Option[PieSectionLabelGenerator] = Option (
    plot.getLabelGenerator
  ) map { _.generateSectionLabel _ }

  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]): Unit = {
    plot.setLabelGenerator(generator.map( lg ⇒
      new org.jfree.chart.labels.PieSectionLabelGenerator {
        override def generateAttributedSectionLabel(dataset: PieDataset, key: Comparable[_]): java.text.AttributedString =
          null
        override def generateSectionLabel(dataset: PieDataset, key: Comparable[_]): String =
          lg(dataset, key)
      }
    ).orNull)
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = for {
    jgenerator <- Option(plot.getToolTipGenerator)
    generator = PieToolTipGenerator.fromPeer(jgenerator)
  } yield generator

  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]): Unit = {
    plot.setToolTipGenerator(
      generator.map(PieToolTipGenerator.toPeer).orNull
    )
  }
}
