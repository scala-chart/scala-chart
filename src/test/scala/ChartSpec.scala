package org.sfree.chart

import Charting._
import org.jfree.chart.plot._

import org.specs2._

class ChartSpec extends Specification { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

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
