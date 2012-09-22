package org.sfree.chart

import org.specs2._

import org.jfree.data.category._
import org.jfree.data.time._
import org.jfree.data.xy._

import DatasetViews._

class DatasetViewsSpec extends Specification { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "XYSeries(Collection)"                                                                           ^
    "Iterable[(Int,Int)] to XYSeries"                                           ! e1               ^
    "Iterable[(Int,Int)] to XYSeriesCollection"                                 ! e2               ^
                                                                                                  p^
  "TimeSeries(Collection)"                                                                         ^
    "Iterable[(Date,Int)] to TimeSeries"                                        ! e3               ^
    "Iterable[(Date,Int)] to TimeSeriesCollection"                              ! e4               ^
                                                                                                  p^
  "TimePeriodValues(Collection)"                                                                   ^
    "Iterable[(Date,Int)] to TimePeriodValues"                                  ! e5               ^
    "Iterable[(Date,Int)] to TimePeriodValuesCollection"                        ! e6               ^
                                                                                                  p^
  "CategoryDataset"                                                                                ^
    "Iterable[(String,Int)] to CategoryDataset"                                 ! e7               ^
    "Iterable[(String,Iterable[(String,Int)])] to CategoryDataset"              ! e9               ^
                                                                                                  p^
  "TimeTableXYDataset"                                                                             ^
    "Iterable[(String,Iterable[(Date,Int)])] to TimeTableXYDataset"             ! e8               ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  implicit def anyWrapper[A](a: A) = new AnyWrapper(a)
  class AnyWrapper[A](a: A) {
    def beViewableAs[B](implicit cm: ClassManifest[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
    def =>=[B](implicit cm: ClassManifest[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
  }

  def d = new java.util.Date(42)
  implicit def jdate2jfree(d: java.util.Date): RegularTimePeriod = new Minute(d)

  def e1 = Iterable((1,2)).=>=[XYSeries]
  def e2 = Iterable((1,2)).=>=[XYSeriesCollection]
  def e3 = Iterable((d,2)).=>=[TimeSeries]
  def e4 = Iterable((d,2)).=>=[TimeSeriesCollection]
  def e5 = Iterable((d,2)).=>=[TimePeriodValues]
  def e6 = Iterable((d,2)).=>=[TimePeriodValuesCollection]
  def e7 = Iterable(("",2)).=>=[CategoryDataset]
  def e8 = Iterable(("",Iterable((d,2)))).=>=[TimeTableXYDataset]
  def e9 = Iterable(("",Iterable(("",2)))).=>=[CategoryDataset]

}
