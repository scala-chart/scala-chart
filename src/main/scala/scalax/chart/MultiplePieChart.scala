package scalax.chart

import org.jfree.chart.plot.MultiplePiePlot

import Imports._

/** Represents categorized numeric data with multiple pies. */
trait MultiplePieChart extends Chart[MultiplePiePlot]
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
