package org.sfree.chart
package views

import org.specs2._

import org.jfree.data.category._
import org.jfree.data.time._
import org.jfree.data.xy._

import CollectionViews._

class CollectionViewsSpec extends Specification with BeViewableAs with Implicits { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "XYSeries(Collection)"                                                                           ^
    "Seq[(Int,Int)] to XYSeries"                                                ! e1               ^
    "Seq[(Int,Int)] to XYSeriesCollection"                                      ! e2               ^
                                                                                                  p^
  "TimeSeries(Collection)"                                                                         ^
    "Seq[(Date,Int)] to TimeSeries"                                             ! e3               ^
    "Seq[(Date,Int)] to TimeSeriesCollection"                                   ! e4               ^
                                                                                                  p^
  "TimePeriodValues(Collection)"                                                                   ^
    "Seq[(Date,Int)] to TimePeriodValues"                                       ! e5               ^
    "Seq[(Date,Int)] to TimePeriodValuesCollection"                             ! e6               ^
                                                                                                  p^
  "CategoryDataset"                                                                                ^
    "Seq[(String,Int)] to CategoryDataset"                                      ! e7               ^
    "Seq[(String,Seq[(String,Int)])] to CategoryDataset"                        ! e9               ^
                                                                                                  p^
  "TimeTableXYDataset"                                                                             ^
    "Seq[(String,Seq[(Date,Int)])] to TimeTableXYDataset"                       ! e8               ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def d = new java.util.Date(42)

  def e1 = Seq((1,2)).=>=[XYSeries]
  def e2 = Seq((1,2)).=>=[XYSeriesCollection]
  def e3 = Seq((d,2)).=>=[TimeSeries]
  def e4 = Seq((d,2)).=>=[TimeSeriesCollection]
  def e5 = Seq((d,2)).=>=[TimePeriodValues]
  def e6 = Seq((d,2)).=>=[TimePeriodValuesCollection]
  def e7 = Seq(("",2)).=>=[CategoryDataset]
  def e8 = Seq(("",Seq((d,2)))).=>=[TimeTableXYDataset]
  def e9 = Seq(("",Seq(("",2)))).=>=[CategoryDataset]

}
