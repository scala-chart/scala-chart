package scalax.chart

import module.Imports._

/** A template class for companion objects of [[Chart]] classes. */
abstract class ChartCompanion[C <: Chart] protected () extends DocMacros {

  /** Returns the underlying plot type. */
  type Plot = C#Plot

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
  final def apply(plot: Plot, title: String, legend: Boolean)(implicit theme: ChartTheme): C = {
    val peer = new JFreeChart(title, org.jfree.chart.JFreeChart.DEFAULT_TITLE_FONT, plot, legend)
    fromPeer(peer, theme)
  }

}
