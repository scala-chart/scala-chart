package scalax.chart

import java.util.Date

import scalax.chart.Charting._

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
      from Coll[(A,B,C)]                                                                  $cde2
      from Coll[(A,Coll[(B,C)])]                                                          $cde3
    implicit conversion
      from Coll[(A,B)]                                                                    $cdi1
      from Coll[(A,B,C)]                                                                  $cdi2
      from Coll[(A,Coll[(B,C)])]                                                          $cdi3

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
    implicit conversion
      from TimePeriodValues                                                               $tpvci1
      from Coll[TimePeriodValues]                                                         $tpvci2

  TimeSeriesCollection
    TimeSeries
      from Coll[(A,B)]                                                                    $ts
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

    val data = List((1,List(2)))
    val dataset = data.toBoxAndWhiskerCategoryDataset()
    dataset must beAnInstanceOf[BoxAndWhiskerCategoryDataset]
  }

  def bwcde2 = {
    implicit val IntNumeric = scala.math.Numeric.IntIsIntegral

    val data = List((1,List((2,List(3)))))
    val dataset = data.toBoxAndWhiskerCategoryDataset
    dataset must beAnInstanceOf[BoxAndWhiskerCategoryDataset]
  }

  def bwxyde1 = {
    implicit val IntNumeric = scala.math.Numeric.IntIsIntegral

    val data = List((new Date,List(1)))
    val dataset = data.toBoxAndWhiskerXYDataset()
    dataset must beAnInstanceOf[BoxAndWhiskerXYDataset]
  }

  def cde1 = {
    val data = List("category" -> 1)
    val dataset = data.toCategoryDataset("series")
    dataset must beAnInstanceOf[CategoryDataset]
  }

  def cde2 = {
    val data = List(("series","category",1))
    val dataset = data.toCategoryDataset
    dataset must beAnInstanceOf[CategoryDataset]
  }

  def cde3 = {
    val data = List("series" -> List("category" -> 1))
    val dataset = data.toCategoryDataset
    dataset must beAnInstanceOf[CategoryDataset]
  }

  def cdi1 = {
    val data = List("category" -> 1)
    hasICD(data)
  }

  def cdi2 = {
    val data = List(("series","category",1))
    hasICD(data)
  }

  def cdi3 = {
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
    val series = List((new Date,1)) toTimePeriodValues "series name"
    series must beAnInstanceOf[TimePeriodValues]
  }

  def tpvce1 = {
    val data = List((new Date,1))
    val dataset = data.toTimePeriodValuesCollection()
    dataset must beAnInstanceOf[TimePeriodValuesCollection]
  }

  def tpvci1 = {
    val data = List((new Date,1)) toTimePeriodValues "series name"
    hasIXYD(data)
  }

  def tpvci2 = {
    val data = List((new Date,1)) toTimePeriodValues "series name"
    hasIXYD(List(data,data))
  }

  def ts = {
    val series = List((new Date,1)) toTimeSeries "series name"
    series must beAnInstanceOf[TimeSeries]
  }

  def tsci1 = {
    val data = List((new Date,1)) toTimeSeries "series name"
    hasIXYD(data)
  }

  def tsci2 = {
    val data = List((new Date,1)) toTimeSeries "series name"
    hasIXYD(List(data,data))
  }

  def xys = {
    val series = List((1,2)) toXYSeries "series name"
    series must beAnInstanceOf[XYSeries]
  }

  def xysce1 = {
    val data = List((1,2))
    val dataset = data.toXYSeriesCollection("series name")
    dataset must beAnInstanceOf[XYSeriesCollection]
  }

  def xysce2 = {
    val data = List("series" -> List((1,2)))
    val dataset = data.toXYSeriesCollection()
    dataset must beAnInstanceOf[XYSeriesCollection]
  }

  def xysci1 = {
    val data = List((1,2)) toXYSeries "series name"
    hasIXYD(data)
  }

  def xysci2 = {
    val s1 = List((1,2)) toXYSeries "series name 1"
    val s2 = List((1,2)) toXYSeries "series name 2"
    hasIXYD(List(s1,s2))
  }

  def xysci3 = {
    val data = List((1,2))
    hasIXYD(data)
  }

  def xysci4 = {
    val data = List("series" -> List((1,2)))
    hasIXYD(data)
  }

  def yis = {
    val series = List((1,3,2,4)) toYIntervalSeries "series name"
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yisce1 = {
    val data = List((1,3,2,4))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yisce2 = {
    val data = List("name" -> List((1,3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yisci1 = {
    val data = List((1,2,3,4)) toYIntervalSeries "series name"
    hasIXYD(data)
  }

  def yisci2 = {
    val data = List((1,2,3,4)) toYIntervalSeries "series name"
    hasIXYD(List(data,data))
  }

  def yisci3 = {
    val data = List((1,3,2,4))
    hasIXYD(data)
  }

  def yisci4 = {
    val data = List("series name" -> List((1,3,2,4)))
    hasIXYD(data)
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def hasICD[A: ToCategoryDataset](a: A) =
    a.toDataset must beAnInstanceOf[CategoryDataset]

  def hasIPD[A: ToPieDataset](a: A) =
    a.toDataset must beAnInstanceOf[PieDataset]

  def hasIXYD[A: ToIntervalXYDataset](a: A) =
    a.toDataset must beAnInstanceOf[IntervalXYDataset]

}
