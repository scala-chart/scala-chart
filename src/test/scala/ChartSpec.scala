package org.sfree.chart

import language.existentials

import org.sfree.chart.Charting._
import org.jfree.chart.plot._
import org.jfree.chart.title.TextTitle

import org.specs2._

class ChartSpec extends Specification { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

  "Chart"                                                                                          ^
    "subtitles is a Buffer[Title]"                                                                 ^
      "append value"                                                            ! cst1             ^
      "prepend value"                                                           ! cst2             ^
      "select value by index"                                                   ! cst3             ^
      "clear all values"                                                        ! cst4             ^
      "insert collection of values"                                             ! cst5             ^
      "provide an iterator"                                                     ! cst6             ^
      "buffer length"                                                           ! cst7             ^
      "remove value"                                                            ! cst8             ^
      "update a value by index"                                                 ! cst9          ^bt^
                                                                                                  p^
  "AreaChart"                                                                                      ^
    "must have a CategoryPlot"                                                  ! ac1              ^
    "stacked must have a CategoryPlot"                                          ! ac2              ^
    "orienting works"                                                           ! ac3              ^
                                                                                                  p^
  "BarChart"                                                                                       ^
    "must have a CategoryPlot"                                                  ! bc1              ^
    "stacked must have a CategoryPlot"                                          ! bc2              ^
    "3D must have a CategoryPlot"                                               ! bc3              ^
    "3D stacked must have a CategoryPlot"                                       ! bc4              ^
    "orienting works"                                                           ! bc5              ^
                                                                                                  p^
  "LineChart"                                                                                      ^
    "must have a CategoryPlot"                                                  ! lc1              ^
    "3D must have a CategoryPlot"                                               ! lc2              ^
    "orienting works"                                                           ! lc3              ^
                                                                                                  p^
  "MultiplePieChart"                                                                               ^
    "must have a MultiplePiePlot"                                               ! mpc1             ^
    "3D must have a MultiplePiePlot"                                            ! mpc2             ^
                                                                                                  p^
  "PieChart"                                                                                       ^
    "must have a PiePlot"                                                       ! pc1              ^
    "3D must have a PiePlot"                                                    ! pc2              ^
                                                                                                  p^
  "RingChart"                                                                                      ^
    "must have a RingPlot"                                                      ! rc1              ^
                                                                                                  p^
  "XYAreaChart"                                                                                    ^
    "must have an XYPlot"                                                       ! xyac1            ^
    "stacked must have an XYPlot"                                               ! xyac2            ^
    "stepped must have an XYPlot"                                               ! xyac3            ^
    "orienting works"                                                           ! xyac4            ^
                                                                                                  p^
  "XYBarChart"                                                                                     ^
    "must have an XYPlot"                                                       ! xybc1            ^
    "orienting works"                                                           ! xybc2            ^
                                                                                                  p^
  "XYLineChart"                                                                                    ^
    "must have an XYPlot"                                                       ! xylc1            ^
    "orienting works"                                                           ! xylc2            ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def cstchart: Chart[_] = {
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
    val chart = AreaChart(categorydataset, orientation = Orientation.Vertical)
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
    val chart = BarChart(categorydataset, orientation = Orientation.Vertical)
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
    val chart = LineChart(categorydataset, orientation = Orientation.Vertical)
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
    val chart = XYAreaChart(xydataset, orientation = Orientation.Vertical)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xybc1 = {
    val chart = XYBarChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xybc2 = {
    val chart = XYBarChart(xydataset, orientation = Orientation.Vertical)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  def xylc1 = {
    val chart = XYLineChart(xydataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xylc2 = {
    val chart = XYLineChart(xydataset, orientation = Orientation.Vertical)
    chart.orientation = Orientation.Horizontal
    chart.orientation === Orientation.Horizontal
  }

  // -----------------------------------------------------------------------------------------------
  // datasets
  // -----------------------------------------------------------------------------------------------

  def points = for { i ← 1 to 2 } yield (i,i)

  def categorydataset = points.toCategoryDataset
  def piedataset = points.toPieDataset
  def tablexydataset = (for { category ← 'a' to 'b' } yield category.toString → points).toCategoryTableXYDataset
  def xydataset = points.toXYSeriesCollection()

}
