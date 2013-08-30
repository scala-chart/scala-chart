package scalax.chart

import language.existentials

import scalax.chart.Charting._

import org.jfree.data.xy._

import org.specs2._

class DatasetSpec extends Specification { def is = s2"""

  YIntervalSeries
    explicit conversion from Coll[(A,B,C,D)]                                              $yisec1
    explicit conversion from Coll[(A,(B,C,D))]                                            $yisec2

  YIntervalSeriesCollection
    explicit conversion from Coll[YIntervalSeries]                                        $yiscec1
    explicit conversion from Coll[(A,B,C,D)]                                              $yiscec2
    explicit conversion from Coll[(A,(B,C,D))]                                            $yiscec3
    explicit conversion from Coll[(A,Coll[(B,C,D,E)])]                                    $yiscec4
    explicit conversion from Coll[(A,Coll[(B,(C,D,E))])]                                  $yiscec5
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def yisec1 = {
    val data = List((1,3,2,4))
    val series = data.toYIntervalSeries()
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yisec2 = {
    val data = Map(1 → (3,2,4))
    val series = data.toYIntervalSeries()
    series must beAnInstanceOf[YIntervalSeries]
  }

  def yiscec1 = {
    val s1 = Map(1 → (3,2,4)).toYIntervalSeries("s1")
    val s2 = Map(1 → (3,2,4)).toYIntervalSeries("s2")
    val dataset = List(s1,s2).toYIntervalSeriesCollection
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec2 = {
    val data = List((1,3,2,4))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec3 = {
    val data = Map(1 → (3,2,4))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec4 = {
    val data = Map("name" → List((1,3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

  def yiscec5 = {
    val data = Map("name" → Map(1 → (3,2,4)))
    val dataset = data.toYIntervalSeriesCollection()
    dataset must beAnInstanceOf[YIntervalSeriesCollection]
  }

}
