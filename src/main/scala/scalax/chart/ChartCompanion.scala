package scalax.chart

import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.StandardChartTheme

/** A template class for companion objects of [[Chart]] classes. */
abstract class ChartCompanion[C <: Chart[_]] protected () {

  /** Returns the theme used as the default for the implicit paramaters in this companion. */
  protected final def DefaultTheme: ChartTheme =
    StandardChartTheme.createJFreeTheme

  /** Returns a new chart using an explicit peer. */
  def fromPeer(peer: JFreeChart): C

  /** Returns a new chart using an explicit peer with the theme applied.
    *
    * @usecase def apply(peer: JFreeChart): C
    *   Returns a new chart using an explicit peer.
    */
  final def apply(peer: JFreeChart)(implicit theme: ChartTheme = DefaultTheme): C = {
    theme.apply(peer)
    fromPeer(peer)
  }

}
