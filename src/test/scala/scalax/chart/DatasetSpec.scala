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

  TimePeriodValues
    explicit conversion
      from Coll[(A,B)]                                                                    $tpve1

  TimePeriodValuesCollection
    explicit conversion
      from Coll[(A,B)]                                                                    $tpvce1
    implicit conversion
      from TimePeriodValues                                                               $tpvci1
      from Coll[TimePeriodValues]                                                         $tpvci2

  TimeSeriesCollection
    implicit conversion
      from Coll[TimeSeries]                                                               $tsci1

  XYSeriesCollection
    explicit conversion
      from Coll[(A,B)]                                                                    $xysce1
      from Coll[(A,Coll[(B,C)])]                                                          $xysce2
    implicit conversion
      from Coll[XYSeries]                                                                 $xysci1

  YIntervalSeries
    explicit conversion
      from Coll[(A,B,C,D)]                                                                $yisec1
      from Coll[(A,(B,C,D))]                                                              $yisec2

  YIntervalSeriesCollection
    explicit conversion
      from Coll[(A,B,C,D)]                                                                $yiscec2
      from Coll[(A,(B,C,D))]                                                              $yiscec3
      from Coll[(A,Coll[(B,C,D,E)])]                                                      $yiscec4
      from Coll[(A,Coll[(B,(C,D,E))])]                                                    $yiscec5
    implicit conversion
      from Coll[YIntervalSeries]                                                          $yisci1
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

  def tpve1 = {
    val data = List((new Date,1))
    val dataset = data.toTimePeriodValues()
    dataset must beAnInstanceOf[TimePeriodValues]
  }

  def tpvce1 = {
    val data = List((new Date,1))
    val dataset = data.toTimePeriodValuesCollection()
    dataset must beAnInstanceOf[TimePeriodValuesCollection]
  }

  def tpvci1 = {
    val data = List((new Date,1)).toTimePeriodValues()
    hasIXYD(data)
  }

  def tpvci2 = {
    val data = List((new Date,1)).toTimePeriodValues()
    hasIXYD(List(data,data))
  }

  def tsci1 = {
    val data = List((new Date,1)).toTimeSeries()
    hasIXYD(List(data,data))
  }

  def yisec1 = {
    val data = List((1,3,2,4))
    val series = data.toYIntervalSeries()
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yisec2 = {
    val data = List(1 -> (3,2,4))
    val series = data.toYIntervalSeries()
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yiscec2 = {
    val data = List((1,3,2,4))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec3 = {
    val data = List(1 -> (3,2,4))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec4 = {
    val data = List("name" -> List((1,3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec5 = {
    val data = List("name" -> List(1 -> (3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yisci1 = {
    val s1 = List(1 -> (3,2,4)).toYIntervalSeries("s1")
    val s2 = List(1 -> (3,2,4)).toYIntervalSeries("s2")
    val data = List(s1,s2)
    hasIXYD(data)
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
    val s1 = List((1,2)).toXYSeries("s1")
    val s2 = List((3,4)).toXYSeries("s2")
    val data = List(s1,s2)
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
