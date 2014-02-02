package org.example

import scalax.chart.Charting._

import org.specs2._

class MarkerSpec extends Specification { def is = s2"""

  CategoryPlot
    can add domain markers                                                                $cp1
    can add range markers                                                                 $cp2

  XYPlot
    can add domain markers                                                                $xyp1
    can add range markers                                                                 $xyp2
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def cp1 = {
    val plot = categoryplot
    plot.domain.markers += 1 += 2L += 3.0 += new CategoryMarker(4.0)
    plot.domain.markers.iterator.length === 4
  }

  def cp2 = {
    val plot = categoryplot
    plot.range.markers += 3 += 3L += 3.0 += (1.0,2.0) += new ValueMarker(3.0)
    plot.range.markers.iterator.length === 5
  }

  def xyp1 = {
    val plot = xyplot
    plot.domain.markers += 3 += 3L += 3.0 += (1.0,2.0) += new ValueMarker(3.0)
    plot.domain.markers.iterator.length === 5
  }

  def xyp2 = {
    val plot = xyplot
    plot.range.markers += 3 += 3L += 3.0 += (1.0,2.0) += new ValueMarker(3.0)
    plot.range.markers.iterator.length === 5
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def categoryplot = {
    val data    = Seq((1,2),(2,4),(3,6),(4,8))
    val dataset = data.toCategoryDataset
    LineChart(dataset).plot
  }

  def xyplot = {
    val data    = Seq((1,2),(2,4),(3,6),(4,8))
    val dataset = data.toXYSeriesCollection("some points")
    XYLineChart(dataset).plot
  }

}
