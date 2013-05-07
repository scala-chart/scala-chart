package scalax.chart
package views

import org.specs2._

import Charting._

import CollectionOfSeriesViews._

import org.jfree.data.time._
import org.jfree.data.xy._

class CollectionOfSeriesViewsSpec extends Specification with BeViewableAs with Implicits { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "TimePeriodValuesCollection"                                                                     ^
    "from Seq[TimePeriodValues]"                                                ! e1               ^
                                                                                                  p^
  "TimeSeriesCollection"                                                                           ^
    "from Seq[TimeSeries]"                                                      ! e2               ^
                                                                                                  p^
  "XYSeriesCollection"                                                                             ^
    "from Seq[XYSeries]"                                                        ! e3               ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def d = new java.util.Date(42)

  def e1 = Seq(Seq((d,2)).toTimePeriodValues()).=>=[TimePeriodValuesCollection]
  def e2 = Seq(Seq((d,2)).toTimeSeries()      ).=>=[TimeSeriesCollection]
  def e3 = Seq(Seq((1,2)).toXYSeries()        ).=>=[XYSeriesCollection]

}
