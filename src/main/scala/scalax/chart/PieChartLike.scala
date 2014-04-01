package scalax.chart

import module.Imports._
import module.PieLabelGenerators._
import module.PieToolTipGenerators._

/** Template trait for pie charts. */
private[chart] trait PieChartLike
    extends Labels[PieLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  self: Chart {
    type Plot <: PiePlot
  } =>

  override def labelGenerator: Option[PieLabelGenerator] = for {
    jgenerator <- Option(plot.getLabelGenerator)
    generator = PieLabelGenerator.fromPeer(jgenerator)
  } yield generator

  override def labelGenerator_=(generator: Option[PieLabelGenerator]): Unit = {
    plot.setLabelGenerator(
      generator.map(PieLabelGenerator.toPeer).orNull
    )
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
