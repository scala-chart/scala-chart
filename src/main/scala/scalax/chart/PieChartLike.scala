package scalax.chart

import Imports._

/** Template trait for pie charts. */
private[chart] trait PieChartLike[P <: PiePlot]
    extends Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  self: Chart[P] ⇒

  override def labelGenerator: Option[PieSectionLabelGenerator] = Option (
    plot.getLabelGenerator
  ) map { _.generateSectionLabel _ }

  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]) {
    plot.setLabelGenerator(generator.map( lg ⇒
      new org.jfree.chart.labels.PieSectionLabelGenerator {
        override def generateAttributedSectionLabel(dataset: PieDataset, key: Comparable[_]): java.text.AttributedString =
          null
        override def generateSectionLabel(dataset: PieDataset, key: Comparable[_]): String =
          lg(dataset, key)
      }
    ).orNull)
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = Option (
    plot.getToolTipGenerator
  ) map { _.generateToolTip _ }

  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]) {
    plot.setToolTipGenerator(generator.map( ttg ⇒
      new org.jfree.chart.labels.PieToolTipGenerator {
        override def generateToolTip(dataset: PieDataset, key: Comparable[_]): String =
          ttg(dataset, key)
      }
    ).orNull)
  }
}
