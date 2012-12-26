package org.sfree.chart

import Charting._
import org.jfree.chart.plot._

import org.specs2._

class ChartSpec extends Specification { def is =

  // -----------------------------------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------------------------------

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
  "XYBarChart"                                                                                     ^
    "must have an XYPlot"                                                       ! xybc1            ^
                                                                                                 end
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

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

  def xybc1 = {
    val data = for {
      category ← 'a' to 'b'
      xys = for { i ← 1 to 5 } yield (i,i)
    } yield category → xys
    val dataset = data.toXYSeriesCollection
    val chart = XYBarChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[XYPlot])
  }

}
