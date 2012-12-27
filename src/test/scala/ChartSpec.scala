package org.sfree.chart

import language.existentials

import Charting._
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
                                                                                                  p^
  "BarChart"                                                                                       ^
    "must have a CategoryPlot"                                                  ! bc1              ^
    "stacked must have a CategoryPlot"                                          ! bc2              ^
    "3D must have a CategoryPlot"                                               ! bc3              ^
    "3D stacked must have a CategoryPlot"                                       ! bc4              ^
                                                                                                  p^
  "LineChart"                                                                                      ^
    "must have a CategoryPlot"                                                  ! lc1              ^
    "3D must have a CategoryPlot"                                               ! lc2              ^
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
                                                                                                  p^
  "XYBarChart"                                                                                     ^
    "must have an XYPlot"                                                       ! xybc1            ^
                                                                                                  p^
  "XYLineChart"                                                                                    ^
    "must have an XYPlot"                                                       ! xylc1            ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def cstchart: Chart[_] = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = AreaChart(dataset)
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
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = AreaChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def ac2 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = AreaChart.stacked(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = BarChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc2 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = BarChart.stacked(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc3 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = BarChart.threeDimensional(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def bc4 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = BarChart.threeDimensionalStacked(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def lc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = LineChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def lc2 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = LineChart.threeDimensional(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[CategoryPlot])
  }

  def mpc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = MultiplePieChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[MultiplePiePlot])
  }

  def mpc2 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toCategoryDataset
    val chart = MultiplePieChart.threeDimensional(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[MultiplePiePlot])
  }

  def pc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toPieDataset
    val chart = PieChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[PiePlot])
  }

  def pc2 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toPieDataset
    val chart = PieChart.threeDimensional(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[PiePlot])
  }

  def rc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toPieDataset
    val chart = RingChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[RingPlot])
  }

  def xyac1 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category → xys
    val dataset = data.toXYSeriesCollection
    val chart = XYAreaChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xyac2 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category.toString → xys
    val dataset = data.toCategoryTableXYDataset
    val chart = XYAreaChart.stacked(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xyac3 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category → xys
    val dataset = data.toXYSeriesCollection
    val chart = XYAreaChart.stepped(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xybc1 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category → xys
    val dataset = data.toXYSeriesCollection
    val chart = XYBarChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

  def xylc1 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category → xys
    val dataset = data.toXYSeriesCollection
    val chart = XYLineChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

}
