package scalax.chart

/** $ChartingInfo */
object Charting extends Charting

/** $ChartingInfo
  *
  * @define ChartingInfo Contains all enrichments. This import is the recommended starting point for
  * working with this package:
  *
  * {{{
  * import scalax.chart._
  * import scalax.chart.Charting._
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = XYLineChart(data)
  * }}}
  *
  * == Marking ==
  *
  * $MarkingInfo
  */
trait Charting extends RichChartingCollections with ChartFactories with RichChart with Imports
    with Marking
