package org.example

import scalax.chart.api._

import org.specs2._

class PlotSpec extends Specification { def is = s2"""

  CategoryPlot
    Axis Label
      can get domain axis label                                                           $cpl1
      can set domain axis label                                                           $cpl2
      can get range axis label                                                            $cpl3
      can set range axis label                                                            $cpl4
    Marking
      can add domain markers                                                              $cpm1
      can add range markers                                                               $cpm2

  XYPlot
    Axis Label
      can get domain axis label                                                           $xypl1
      can set domain axis label                                                           $xypl2
      can get range axis label                                                            $xypl3
      can set range axis label                                                            $xypl4
    Marking
      can add domain markers                                                              $xypm1
      can add range markers                                                               $xypm2
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // axis labels
  // -----------------------------------------------------------------------------------------------

  def Label = "Test Label"

  def cpl1 = {
    val chart = UnlabeledCategoryChart
    chart.plot.domain.axis.label.text === ""
  }

  def cpl2 = {
    val chart = UnlabeledCategoryChart
    chart.plot.domain.axis.label.text = Label
    chart.plot.getDomainAxis.getLabel === Label
  }

  def cpl3 = {
    val chart = UnlabeledCategoryChart
    chart.plot.range.axis.label.text === ""
  }

  def cpl4 = {
    val chart = UnlabeledCategoryChart
    chart.plot.range.axis.label.text = Label
    chart.plot.getRangeAxis.getLabel === Label
  }

  def xypl1 = {
    val chart = UnlabeledXYChart
    chart.plot.domain.axis.label.text === ""
  }

  def xypl2 = {
    val chart = UnlabeledXYChart
    chart.plot.domain.axis.label.text = Label
    chart.plot.getDomainAxis.getLabel === Label
  }

  def xypl3 = {
    val chart = UnlabeledXYChart
    chart.plot.range.axis.label.text === ""
  }

  def xypl4 = {
    val chart = UnlabeledXYChart
    chart.plot.range.axis.label.text = Label
    chart.plot.getRangeAxis.getLabel === Label
  }

  // -----------------------------------------------------------------------------------------------
  // marking
  // -----------------------------------------------------------------------------------------------

  def cpm1 = {
    val chart = UnlabeledCategoryChart
    chart.plot.domain.markers += 1 += 2L += 3.0 += new CategoryMarker(4.0)
    chart.plot.domain.markers.iterator.length === 4
  }

  def cpm2 = {
    val chart = UnlabeledCategoryChart
    chart.plot.range.markers += 3 += 3L += 3.0 += ((1.0,2.0)) += new ValueMarker(3.0)
    chart.plot.range.markers.iterator.length === 5
  }

  def xypm1 = {
    val chart = UnlabeledXYChart
    chart.plot.domain.markers += 3 += 3L += 3.0 += ((1.0,2.0)) += new ValueMarker(3.0)
    chart.plot.domain.markers.iterator.length === 5
  }

  def xypm2 = {
    val chart = UnlabeledXYChart
    chart.plot.range.markers += 3 += 3L += 3.0 += ((1.0,2.0)) += new ValueMarker(3.0)
    chart.plot.range.markers.iterator.length === 5
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def UnlabeledCategoryChart = {
    val data = List("series" -> List("category" -> 1))
    LineChart(data)
  }

  def UnlabeledXYChart = {
    val data = for (i <- 1 to 4) yield (i,i)
    XYLineChart(data)
  }

}
