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
                                                                                                  p^
  "PieChart"                                                                                       ^
    "must have a PiePlot"                                                       ! pc1              ^
                                                                                                  p^
  "RingChart"                                                                                      ^
    "must have a RingPlot"                                                      ! rc1              ^
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

  def pc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toPieDataset
    val chart = PieChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[PiePlot])
  }

  def rc1 = {
    val data = for { i ← 1 to 5 } yield (i,i)
    val dataset = data.toPieDataset
    val chart = RingChart(dataset)
    (chart.plot must not (throwA[ClassCastException])) and (chart.plot must beAnInstanceOf[RingPlot])
  }

}
