package scalax.chart
package module

/** $ChartingInfo */
object Charting extends Charting

/** $ChartingInfo
  *
  * @define ChartingInfo Contains all enrichments. This import is the recommended starting point for
  * working with this library:
  *
  * {{{
  * import scalax.chart.api._
  *
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = XYLineChart(data)
  * }}}
  *
  * == Exporting Charts ==
  *
  * $ExportingInfo
  *
  * == Marking ==
  *
  * $MarkingInfo
  */
trait Charting extends RichChartingCollections with ChartFactories with RichChart with Imports
    with Marking with Exporting with RichPlot
