package scalax.chart
package views

import org.specs2._

import Charting._

import SeriesViews._

import org.jfree.data.time._
import org.jfree.data.xy._

class SeriesViewsSpec extends Specification with BeViewableAs with Implicits { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "TimePeriodValuesCollection"                                                                     ^
    "from TimePeriodValues"                                                     ! e1               ^
                                                                                                  p^
  "TimeSeriesCollection"                                                                           ^
    "from TimeSeries"                                                           ! e2               ^
                                                                                                  p^
  "XYSeriesCollection"                                                                             ^
    "from XYSeries"                                                             ! e3               ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def d = new java.util.Date(42)

  def e1 = Seq((d,2)).toTimePeriodValues().=>=[TimePeriodValuesCollection]
  def e2 = Seq((d,2)).toTimeSeries()      .=>=[TimeSeriesCollection]
  def e3 = Seq((1,2)).toXYSeries()        .=>=[XYSeriesCollection]

}
