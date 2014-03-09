package org.example

import java.util.Date

import scalax.chart.api._

import Implicits.jdate2jfree

import org.specs2._

class DatasetSpec extends Specification { def is = s2"""

  BoxAndWhiskerCategoryDataset
    explicit conversion
      from Coll[(A,Coll[B])]                                                              $bwcde1
      from Coll[(A,Coll[(B,Coll[C])])]                                                    $bwcde2

  BoxAndWhiskerXYDataset
    explicit conversion
      from Coll[(A,Coll[B])]                                                              $bwxyde1

  CategoryDataset
    explicit conversion
      from Coll[(A,B)]                                                                    $cde1
      from Coll[(A,Coll[(B,C)])]                                                          $cde2
    implicit conversion
      from Coll[(A,B)]                                                                    $cdi1
      from Coll[(A,Coll[(B,C)])]                                                          $cdi2

  PieDataset
    explicit conversion
      from Coll[(A,B)]                                                                    $pde1
    implicit conversion
      from Coll[(A,B)]                                                                    $pdi1

  TimePeriodValuesCollection
    TimePeriodValues
      from Coll[(A,B)]                                                                    $tpv
    explicit conversion
      from Coll[(A,B)]                                                                    $tpvce1
      from Coll[(A,Coll[(B,C)])]                                                          $tpvce2
    implicit conversion
      from TimePeriodValues                                                               $tpvci1
      from Coll[TimePeriodValues]                                                         $tpvci2

  TimeSeriesCollection
    TimeSeries
      from Coll[(A,B)]                                                                    $ts
    explicit conversion
      from Coll[(A,B)]                                                                    $tsce1
      from Coll[(A,Coll[(B,C)])]                                                          $tsce2
    implicit conversion
      from TimeSeries                                                                     $tsci1
      from Coll[TimeSeries]                                                               $tsci2

  XYSeriesCollection
    XYSeries
      from Coll[(A,B)]                                                                    $xys
    explicit conversion
      from Coll[(A,B)]                                                                    $xysce1
      from Coll[(A,Coll[(B,C)])]                                                          $xysce2
    implicit conversion
      from XYSeries                                                                       $xysci1
      from Coll[XYSeries]                                                                 $xysci2
      from Coll[(A,B)]                                                                    $xysci3
      from Coll[(A,Coll[(B,C)])]                                                          $xysci4

  YIntervalSeriesCollection
    YIntervalSeries
      from Coll[(A,B,C,D)]                                                                $yis
    explicit conversion
      from Coll[(A,B,C,D)]                                                                $yisce1
      from Coll[(A,Coll[(B,C,D,E)])]                                                      $yisce2
    implicit conversion
      from YIntervalSeries                                                                $yisci1
      from Coll[YIntervalSeries]                                                          $yisci2
      from Coll[(A,B,C,D)]                                                                $yisci3
      from Coll[(A,Coll[(B,C,D,E)])]                                                      $yisci4
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def bwcde1 = {
    implicit val IntNumeric = scala.math.Numeric.IntIsIntegral

    val data = List("category" -> List(2))
    val dataset = data.toBoxAndWhiskerCategoryDataset("series")
    dataset must beAnInstanceOf[BoxAndWhiskerCategoryDataset]
  }

  def bwcde2 = {
    implicit val IntNumeric = scala.math.Numeric.IntIsIntegral

    val data = List("series" -> List("category" -> List(3)))
    val dataset = data.toBoxAndWhiskerCategoryDataset
    dataset must beAnInstanceOf[BoxAndWhiskerCategoryDataset]
  }

  def bwxyde1 = {
    implicit val IntNumeric = scala.math.Numeric.IntIsIntegral

    val data = List(new Date -> List(1))
    val dataset = data.toBoxAndWhiskerXYDataset("series")
    dataset must beAnInstanceOf[BoxAndWhiskerXYDataset]
  }

  def cde1 = {
    val data = List("category" -> 1)
    val dataset = data.toCategoryDataset("series")
    dataset must beAnInstanceOf[CategoryDataset]
  }

  def cde2 = {
    val data = List("series" -> List("category" -> 1))
    val dataset = data.toCategoryDataset
    dataset must beAnInstanceOf[CategoryDataset]
  }

  def cdi1 = {
    val data = List("category" -> 1)
    hasICD(data)
  }

  def cdi2 = {
    val data = List("series" -> List("category" -> 1))
    hasICD(data)
  }

  def pde1 = {
    val data = List("category" -> 1)
    val dataset = data.toPieDataset
    dataset must beAnInstanceOf[PieDataset]
  }

  def pdi1 = {
    val data = List("category" -> 1)
    hasIPD(data)
  }

  def tpv = {
    val series = List((new Date,1)).toTimePeriodValues("series")
    series must beAnInstanceOf[TimePeriodValues]
  }

  def tpvce1 = {
    val data = List((new Date,1))
    val dataset = data.toTimePeriodValuesCollection("series")
    dataset must beAnInstanceOf[TimePeriodValuesCollection]
  }

  def tpvce2 = {
    val data = List("series" -> List((new Date,1)))
    val dataset = data.toTimePeriodValuesCollection
    dataset must beAnInstanceOf[TimePeriodValuesCollection]
  }

  def tpvci1 = {
    val series = List((new Date,1)).toTimePeriodValues("series")
    hasIXYD(series) and hasXYD(series)
  }

  def tpvci2 = {
    val series = List((new Date,1)).toTimePeriodValues("series")
    val dataset = List(series,series)
    hasIXYD(dataset) and hasXYD(dataset)
  }

  def ts = {
    val series = List((new Date,1)).toTimeSeries("series")
    series must beAnInstanceOf[TimeSeries]
  }

  def tsce1 = {
    val data = List((new Date,1))
    val dataset = data.toTimeSeriesCollection("series")
    dataset must beAnInstanceOf[TimeSeriesCollection]
  }

  def tsce2 = {
    val data = List("series" -> List((new Date,1)))
    val dataset = data.toTimeSeriesCollection
    dataset must beAnInstanceOf[TimeSeriesCollection]
  }

  def tsci1 = {
    val series = List((new Date,1)).toTimeSeries("series")
    hasIXYD(series) and hasXYD(series)
  }

  def tsci2 = {
    val series = List((new Date,1)).toTimeSeries("series")
    val dataset = List(series,series)
    hasIXYD(dataset) and hasXYD(dataset)
  }

  def xys = {
    val series = List((1,2)).toXYSeries("series")
    series must beAnInstanceOf[XYSeries]
  }

  def xysce1 = {
    val data = List((1,2))
    val dataset = data.toXYSeriesCollection("series")
    dataset must beAnInstanceOf[XYSeriesCollection]
  }

  def xysce2 = {
    val data = List("series" -> List((1,2)))
    val dataset = data.toXYSeriesCollection()
    dataset must beAnInstanceOf[XYSeriesCollection]
  }

  def xysci1 = {
    val series = List((1,2)).toXYSeries("series")
    hasIXYD(series) and hasXYD(series)
  }

  def xysci2 = {
    val series1 = List((1,2)).toXYSeries("series 1")
    val series2 = List((1,2)).toXYSeries("series 2")
    val dataset = List(series1,series2)
    hasIXYD(dataset) and hasXYD(dataset)
  }

  def xysci3 = {
    val data = List((1,2))
    hasIXYD(data) and hasXYD(data)
  }

  def xysci4 = {
    val data = List("series" -> List((1,2)))
    hasIXYD(data) and hasXYD(data)
  }

  def yis = {
    val series = List((1,3,2,4)).toYIntervalSeries("series")
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yisce1 = {
    val data = List((1,3,2,4))
    val dataset = data.toYIntervalSeriesCollection("series")
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yisce2 = {
    val data = List("series" -> List((1,3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yisci1 = {
    val series = List((1,2,3,4)).toYIntervalSeries("series")
    hasIXYD(series) and hasXYD(series)
  }

  def yisci2 = {
    val series = List((1,2,3,4)).toYIntervalSeries("series")
    val dataset = List(series,series)
    hasIXYD(dataset) and hasXYD(dataset)
  }

  def yisci3 = {
    val data = List((1,3,2,4))
    hasIXYD(data) and hasXYD(data)
  }

  def yisci4 = {
    val data = List("series" -> List((1,3,2,4)))
    hasIXYD(data) and hasXYD(data)
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def hasICD[A: ToCategoryDataset](a: A) =
    ToCategoryDataset[A].toDataset(a) must beAnInstanceOf[CategoryDataset]

  def hasIPD[A: ToPieDataset](a: A) =
    ToPieDataset[A].toDataset(a) must beAnInstanceOf[PieDataset]

  def hasIXYD[A: ToIntervalXYDataset](a: A) =
    ToIntervalXYDataset[A].toDataset(a) must beAnInstanceOf[IntervalXYDataset]

  def hasXYD[A: ToXYDataset](a: A) =
    ToXYDataset[A].toDataset(a) must beAnInstanceOf[XYDataset]

}
