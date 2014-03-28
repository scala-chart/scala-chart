package org.example

import scalax.chart.api._

import org.specs2._

class ToolTipGeneratorSpec extends Specification { def is = s2"""

  Tool Tip Generator Specification

  PieToolTipGenerator
    Creation
      from ordinary function                                                              $pie1
      using companion to avoid typing                                                     $pie2
      using companion value to String function                                            $pie3
      using companion default                                                             $pie4
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def pie1 = {
    val chart = piechart
    chart.tooltipGenerator = (dataset: PieDataset, key: Comparable[_]) => {
      dataset.getValue(key).toString
    }

    chart.tooltipGenerator must beSome
  }

  def pie2 = {
    val chart = piechart
    chart.tooltipGenerator = PieToolTipGenerator(_.getValue(_).toString)
    chart.tooltipGenerator must beSome
  }

  def pie3 = {
    val chart = piechart
    chart.tooltipGenerator = PieToolTipGenerator(_.toString)
    chart.tooltipGenerator must beSome
  }

  def pie4 = {
    val chart = piechart
    chart.tooltipGenerator = PieToolTipGenerator.Default
    chart.tooltipGenerator must beSome
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def piechart = {
    val data = for (i <- 1 to 5) yield (i,i)
    PieChart(data, tooltips = false)
  }

}
