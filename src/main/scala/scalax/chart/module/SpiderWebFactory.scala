package scalax.chart
package module

/** $SpiderWebChartFactoryInfo */
object SpiderWebChartFactory extends SpiderWebChartFactory

/** $SpiderWebChartFactoryInfo
  *
  * @define SpiderWebChartFactoryInfo Contains factories to create pie charts and other pie-like chart.
  */
trait SpiderWebChartFactory {

  /** Factory for pie charts. */
  val SpiderWebChart = scalax.chart.SpiderWebChart

}
