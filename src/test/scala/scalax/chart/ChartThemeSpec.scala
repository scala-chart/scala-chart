package org.example

import scalax.chart.api._

import org.specs2._

class ChartThemeSpec extends Specification { def is = s2"""

  ChartTheme
    values get applied correctly                                                          ${a.pendingUntilFixed}
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def a = {
    val transparent = new Color(0xFF, 0xFF, 0xFF, 0)

    implicit val theme = new org.jfree.chart.StandardChartTheme("my theme")

    theme.setPlotBackgroundPaint(transparent)
    theme.setChartBackgroundPaint(transparent)
    theme.setLegendBackgroundPaint(transparent)

    val chart = AreaChart(catpoints, title = "foo")

    (chart.plot.getBackgroundPaint === transparent) &&
    (chart.peer.getBackgroundPaint === transparent) &&
    (chart.peer.getTitle.getBackgroundPaint === transparent) // null because of text title does not
                                                              // apply background paint
  }

  // -----------------------------------------------------------------------------------------------
  // datasets
  // -----------------------------------------------------------------------------------------------

  def points = Vector.tabulate(2)(i ⇒ (i,i))
  def catpoints = Vector("foo" -> points)

}
