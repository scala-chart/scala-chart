package scalax.chart

import org.jfree.chart.plot.RingPlot

/** Represents categorized numeric data with a ring. */
trait RingChart extends Chart[RingPlot] with PieChartLike[RingPlot] {
  override def plot: RingPlot = peer.getPlot.asInstanceOf[RingPlot]
}
