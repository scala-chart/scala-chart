package scalax.chart
package module

/** $PieChartFactoriesInfo */
object PieChartFactories extends PieChartFactories

/** $PieChartFactoriesInfo
  *
  * @define PieChartFactoriesInfo Contains factories to create pie charts and other pie-like chart.
  */
trait PieChartFactories {

  /** Factory for multiple pie charts. */
  val MultiplePieChart = scalax.chart.MultiplePieChart

  /** Factory for pie charts. */
  val PieChart = scalax.chart.PieChart

  /** Factory for ring charts. */
  val RingChart = scalax.chart.RingChart

}
