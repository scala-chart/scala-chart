package scalax.chart

import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.Plot

/** A template class for companion objects of [[Chart]] classes. */
abstract class ChartCompanion[P <: Plot,C <: Chart[P]] protected () extends DocMacros {

  /** Returns a new $chart using an explicit peer.
    *
    * @param jfree $peer
    */
  def fromPeer(jfree: JFreeChart): C

  /** Returns a new $chart using an explicit peer with the theme applied.
    *
    * @param jfree $peer
    * @param theme $theme
    */
  final def fromPeer(jfree: JFreeChart, theme: ChartTheme): C = {
    theme.apply(jfree)
    fromPeer(jfree)
  }

  /** Creates a new $chart.
    *
    * @param plot the plot that is used to create the $chart
    * @param title $title
    * @param legend $legend
    * @param theme $theme
    */
  final def apply(plot: P, title: String, legend: Boolean, theme: ChartTheme): C = {
    val peer = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
    fromPeer(peer, theme)
  }

}
