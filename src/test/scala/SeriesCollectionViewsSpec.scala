package org.sfree.chart

import org.specs2._

import BeViewableAs._

import Charting._
import SeriesCollectionViews._

import org.jfree.data.time._
import org.jfree.data.xy._

class SeriesCollectionViewsSpec extends Specification { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "TimePeriodValuesCollection"                                                                     ^
    "from TimePeriodValues"                                                     ! e1               ^
    "from Iterable[TimePeriodValues]"                                           ! e2               ^
                                                                                                  p^
  "TimeSeriesCollection"                                                                           ^
    "from TimeSeries"                                                           ! e3               ^
    "from Iterable[TimeSeries]"                                                 ! e4               ^
                                                                                                  p^
  "XYSeriesCollection"                                                                             ^
    "from XYSeries"                                                             ! e5               ^
    "from Iterable[XYSeries]"                                                   ! e6               ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def d = new java.util.Date(42)
  implicit def jdate2jfree(d: java.util.Date): RegularTimePeriod = new Minute(d)

  def e1 =          Iterable((d,2)).toTimePeriodValues() .=>=[TimePeriodValuesCollection]
  def e2 = Iterable(Iterable((d,2)).toTimePeriodValues()).=>=[TimePeriodValuesCollection]
  def e3 =          Iterable((d,2)).toTimeSeries()       .=>=[TimeSeriesCollection]
  def e4 = Iterable(Iterable((d,2)).toTimeSeries()      ).=>=[TimeSeriesCollection]
  def e5 =          Iterable((1,2)).toXYSeries()         .=>=[XYSeriesCollection]
  def e6 = Iterable(Iterable((1,2)).toXYSeries()        ).=>=[XYSeriesCollection]

}
