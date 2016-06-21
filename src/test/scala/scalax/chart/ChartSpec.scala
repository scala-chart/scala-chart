package org.example

import scalax.chart.api._

import org.jfree.chart.plot._
import org.jfree.chart.title.TextTitle

import org.specs2._

class ChartSpec extends Specification { def is = s2"""

  Chart
    anti-aliasing value                                                                   $caa1
    background paint value                                                                $cbp1
    title value                                                                           $ct1
    subtitles is a Buffer[Title]
      append value                                                                        $cst1
      prepend value                                                                       $cst2
      select value by index                                                               $cst3
      clear all values                                                                    $cst4
      insert collection of values                                                         $cst5
      provide an iterator                                                                 $cst6
      buffer length                                                                       $cst7
      remove value                                                                        $cst8
      update a value by index                                                             $cst9

  AreaChart
    must have a CategoryPlot                                                              $ac1
    stacked must have a CategoryPlot                                                      $ac2
    orienting works                                                                       $ac3

  BarChart
    must have a CategoryPlot                                                              $bc1
    stacked must have a CategoryPlot                                                      $bc2
    3D must have a CategoryPlot                                                           $bc3
    3D stacked must have a CategoryPlot                                                   $bc4
    orienting works                                                                       $bc5

  BoxAndWhiskerChart
    must have a CategoryPlot                                                              $bwc1
    orienting works                                                                       $bwc2

  LineChart
    must have a CategoryPlot                                                              $lc1
    3D must have a CategoryPlot                                                           $lc2
    orienting works                                                                       $lc3

  MultiplePieChart
    must have a MultiplePiePlot                                                           $mpc1
    3D must have a MultiplePiePlot                                                        $mpc2

  PieChart
    must have a PiePlot                                                                   $pc1
    3D must have a PiePlot                                                                $pc2

  RingChart
    must have a RingPlot                                                                  $rc1

  XYAreaChart
    must have an XYPlot                                                                   $xyac1
    stacked must have an XYPlot                                                           $xyac2
    stepped must have an XYPlot                                                           $xyac3
    orienting works                                                                       $xyac4

  XYBarChart
    must have an XYPlot                                                                   $xybc1
    stacked must have an XYPlot                                                           $xybc2
    orienting works                                                                       $xybc3

  XYBoxAndWhiskerChart
    must have a XYPlot                                                                    $xybwc1
    orienting works                                                                       $xybwc2

  XYDeviationChart
    must have an XYPlot                                                                   $xydc1
    orienting works                                                                       $xydc2

  XYLineChart
    must have an XYPlot                                                                   $xylc1
    orienting works                                                                       $xylc2
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def caa1 = {
    val chart = AreaChart(categorydataset)
    val t = "bar"
    chart.title = t
    chart.title === t
  }

  def cbp1 = {
    val chart = AreaChart(categorydataset)
    val c = new Color(42,42,42)
    chart.backgroundPaint = c
    chart.backgroundPaint === c
  }

  def ct1 = {
    val chart = AreaChart(categorydataset)
    chart.peer.setAntiAlias(false)
    chart.antiAlias = true
    chart.antiAlias === true
  }

  def cstchart = {
    val chart = AreaChart(categorydataset)
    chart.peer.clearSubtitles()
    chart
  }

  def cst1 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    chart.peer.addSubtitle(t1)
    chart.subtitles += t2
    chart.peer.getSubtitle(1) === t2
  }

  def cst2 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    chart.peer.addSubtitle(t1)
    t2 +=: chart.subtitles
    chart.peer.getSubtitle(0) === t2
  }

  def cst3 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    chart.peer.addSubtitle(t1)
    chart.peer.addSubtitle(t2)
    chart.subtitles(1) === t2
  }

  def cst4 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    chart.peer.addSubtitle(t1)
    chart.peer.addSubtitle(t2)
    chart.subtitles.clear()
    chart.peer.getSubtitleCount === 0
  }

  def cst5 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    val ts = Seq(t1, t2)
    chart.subtitles.insertAll(0, ts)
    (chart.peer.getSubtitleCount === 2) and
    (chart.peer.getSubtitle(0) === t1) and
    (chart.peer.getSubtitle(1) === t2)
  }

  def cst6 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    chart.peer.addSubtitle(t1)
    chart.peer.addSubtitle(t2)
    val it = chart.subtitles.iterator
    (it.next === t1) and (it.next === t2) and (it.hasNext === false)
  }

  def cst7 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    chart.peer.addSubtitle(t1)
    chart.subtitles.length === 1
  }

  def cst8 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    chart.peer.addSubtitle(t1)
    chart.subtitles -= t1
    chart.peer.getSubtitleCount === 0
  }

  def cst9 = {
    val chart = cstchart
    val t1 = new TextTitle("t1")
    val t2 = new TextTitle("t2")
    val t3 = new TextTitle("t3")
    chart.peer.addSubtitle(t1)
    chart.peer.addSubtitle(t2)
    chart.subtitles(1) = t3
    chart.peer.getSubtitle(1) === t3
  }

  def ac1 = {
    val chart = AreaChart(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def ac2 = {
    val chart = AreaChart.stacked(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def ac3 = {
    val chart = AreaChart(categorydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def bc1 = {
    val chart = BarChart(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc2 = {
    val chart = BarChart.stacked(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc3 = {
    val chart = BarChart.threeDimensional(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc4 = {
    val chart = BarChart.threeDimensionalStacked(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc5 = {
    val chart = BarChart(categorydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def bwc1 = {
    val chart = BoxAndWhiskerChart(bwcategorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bwc2 = {
    val chart = BoxAndWhiskerChart(bwcategorydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def lc1 = {
    val chart = LineChart(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def lc2 = {
    val chart = LineChart.threeDimensional(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def lc3 = {
    val chart = LineChart(categorydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def mpc1 = {
    val chart = MultiplePieChart(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[MultiplePiePlot])
  }

  def mpc2 = {
    val chart = MultiplePieChart.threeDimensional(categorydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[MultiplePiePlot])
  }

  def pc1 = {
    val chart = PieChart(piedataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[PiePlot])
  }

  def pc2 = {
    val chart = PieChart.threeDimensional(piedataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[PiePlot])
  }

  def rc1 = {
    val chart = RingChart(piedataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[RingPlot])
  }

  def xyac1 = {
    val chart = XYAreaChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xyac2 = {
    val chart = XYAreaChart.stacked(tablexydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xyac3 = {
    val chart = XYAreaChart.stepped(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xyac4 = {
    val chart = XYAreaChart(xydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xybc1 = {
    val chart = XYBarChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xybc2 = {
    val chart = XYBarChart.stacked(tablexydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xybc3 = {
    val chart = XYBarChart(xydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xybwc1 = {
    val chart = XYBoxAndWhiskerChart(bwxydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xybwc2 = {
    val chart = XYBoxAndWhiskerChart(bwxydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xydc1 = {
    val chart = XYDeviationChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xydc2 = {
    val chart = XYDeviationChart(xydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xylc1 = {
    val chart = XYLineChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xylc2 = {
    val chart = XYLineChart(xydataset)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  // -----------------------------------------------------------------------------------------------
  // datasets
  // -----------------------------------------------------------------------------------------------

  def points = Vector.tabulate(2)(i ⇒ (i,i))
  def catpoints = Vector("foo" -> points)

  implicit val IntNumeric = Numeric.IntIsIntegral

  def bwcategorydataset = Vector.tabulate(2)(i => (i,i to i))
  def bwxydataset = Vector.tabulate(1)(i => (new java.util.Date(i.toLong * 1000),i to i))
  def categorydataset = catpoints
  def piedataset = points
  def tablexydataset = catpoints
  def xydataset = points

}
