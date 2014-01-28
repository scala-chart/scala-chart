package scalax.chart

import Imports._

/** Represents categorized numeric data with a pie. */
trait PieChart extends Chart[PiePlot] with PieChartLike[PiePlot] {
  override def plot: PiePlot = peer.getPlot.asInstanceOf[PiePlot]
}
