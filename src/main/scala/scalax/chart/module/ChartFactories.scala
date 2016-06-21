package scalax.chart
package module

/** $ChartFactoriesInfo */
object ChartFactories extends ChartFactories

/** $ChartFactoriesInfo
  *
  * @define ChartFactoriesShortInfo [[ChartFactories]] contains all high-level factories to
  * conveniently create charts.
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = XYLineChart(data)
  * }}}
  *
  * @define ChartFactoriesInfo '' '' $ChartFactoriesShortInfo
  *
  * == Creating Charts ==
  *
  * The only argument needed to create a chart is a dataset:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val dataset = data.toXYSeriesCollection("some data")
  * val chart = XYLineChart(dataset)
  * }}}
  *
  * == Creating Charts with Themes ==
  *
  * The default theme used is the JFreeChart theme. To apply a different theme to the charts created
  * by the factories, simply define an implicit chart theme in scope, e.g. the darkness theme from
  * JFreeChart:
  *
  * {{{
  * implicit val theme = org.jfree.chart.StandardChartTheme.createDarknessTheme
  * }}}
  */
trait ChartFactories
    extends BoxAndWhiskerChartFactories
    with CategoryChartFactories
    with PieChartFactories
    with SpiderWebChartFactory
    with XYChartFactories
    with DatasetConversions
    with DocMacros
