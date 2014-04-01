package org.example

import scalax.chart.api._

import org.specs2._

class LabelGeneratorSpec extends Specification { def is = s2"""

  Label Generator Specification

  Category Label Generator
    Creation
      from ordinary function                                                              $cat1
      using companion to avoid typing                                                     $cat2
      using companion value to String function                                            $cat3
      using companion default                                                             $cat4

  Pie Label Generator
    Creation
      from ordinary function                                                              $pie1
      using companion to avoid typing                                                     $pie2
      using companion value to String function                                            $pie3
      using companion default                                                             $pie4

  XY Label Generator
    Creation
      from ordinary function                                                              $xy1
      using companion to avoid typing                                                     $xy2
      using companion value to String function                                            $xy3
      using companion (x,y) to String function                                            $xy4
      using companion default                                                             $xy5
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def cat1 = {
    val chart = catchart
    chart.labelGenerator = (dataset: CategoryDataset, series: Comparable[_], category: Comparable[_]) => {
      dataset.getValue(series,category).toString
    }

    chart.labelGenerator must beSome
  }

  def cat2 = {
    val chart = catchart
    chart.labelGenerator = CategoryLabelGenerator(_.getValue(_,_).toString)
    chart.labelGenerator must beSome
  }

  def cat3 = {
    val chart = catchart
    chart.labelGenerator = CategoryLabelGenerator(_.toString)
    chart.labelGenerator must beSome
  }

  def cat4 = {
    val chart = catchart
    chart.labelGenerator = CategoryLabelGenerator.Default
    chart.labelGenerator must beSome
  }

  def pie1 = {
    val chart = piechart
    chart.labelGenerator = (dataset: PieDataset, key: Comparable[_]) => {
      dataset.getValue(key).toString
    }

    chart.labelGenerator must beSome
  }

  def pie2 = {
    val chart = piechart
    chart.labelGenerator = PieLabelGenerator(_.getValue(_).toString)
    chart.labelGenerator must beSome
  }

  def pie3 = {
    val chart = piechart
    chart.labelGenerator = PieLabelGenerator(_.toString)
    chart.labelGenerator must beSome
  }

  def pie4 = {
    val chart = piechart
    chart.labelGenerator = PieLabelGenerator.Default
    chart.labelGenerator must beSome
  }

  def xy1 = {
    val chart = xychart
    chart.labelGenerator = (dataset: XYDataset, series: Comparable[_], item: Int) => {
      val idx = dataset.indexOf(series)
      dataset.getY(idx, item).toString
    }

    chart.labelGenerator must beSome
  }

  def xy2 = {
    val chart = xychart
    chart.labelGenerator = XYLabelGenerator { (dataset, series, item) =>
      val idx = dataset.indexOf(series)
      dataset.getY(idx, item).toString
    }

    chart.labelGenerator must beSome
  }

  def xy3 = {
    val chart = xychart
    chart.labelGenerator = XYLabelGenerator(_.toString)
    chart.labelGenerator must beSome
  }

  def xy4 = {
    val chart = xychart
    chart.labelGenerator = XYLabelGenerator((x,y) => s"""($x,$y)""")
    chart.labelGenerator must beSome
  }

  def xy5 = {
    val chart = xychart
    chart.labelGenerator = XYLabelGenerator.Default
    chart.labelGenerator must beSome
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def catchart = {
    val data = for (i <- 1 to 5) yield (i,i)
    BarChart(data)
  }

  def piechart = {
    val data = for (i <- 1 to 5) yield (i,i)
    PieChart(data)
  }

  def xychart = {
    val data = for (i <- 1 to 5) yield (i,i)
    XYLineChart(data)
  }

}
