package scalax.chart

import module._

/** Contains all enrichments. This import is the recommended starting point for working with this
  * library:
  *
  * {{{
  * import scalax.chart._
  * import scalax.chart.api._
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = XYLineChart(data)
  * }}}
  *
  * == Marking ==
  *
  * $MarkingInfo
  */
object api extends RichChartingCollections with ChartFactories with RichChart with Imports
    with Marking
