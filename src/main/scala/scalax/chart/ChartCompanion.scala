package scalax.chart

import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart

/** A template class for companion objects of [[Chart]] classes. */
abstract class ChartCompanion[C <: Chart[_]] protected () extends DocMacros {

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

}
