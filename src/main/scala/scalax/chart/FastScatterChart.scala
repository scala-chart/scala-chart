package scalax.chart

import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.FastScatterPlot

import module.Imports._

/** A fast scatter chart. */
abstract class FastScatterChart protected () extends Chart {
  type Plot = FastScatterPlot
  override def plot: FastScatterPlot = peer.getPlot.asInstanceOf[FastScatterPlot]
}

/** Factory for ${chart}s.
  *
  * @define chart FastScatterChart
  * @define Chart FastScatterChart
  */
object FastScatterChart extends ChartCompanion[FastScatterChart] {
  override final def fromPeer(jfree: JFreeChart): FastScatterChart = {
    require(jfree.getPlot.isInstanceOf[FastScatterPlot], "Illegal peer plot type.")

    new FastScatterChart {
      override final lazy val peer = jfree
    }
  }

  def apply(data: Array[Array[Float]])(implicit theme: ChartTheme = ChartTheme.Default): FastScatterChart = {
    val domainAxis = new NumberAxis()
    domainAxis.setAutoRangeIncludesZero(false)
    val rangeAxis = new NumberAxis()

    val plot = new FastScatterPlot(data, domainAxis, rangeAxis)

    FastScatterChart(plot, title = "", legend = true)
  }
}
