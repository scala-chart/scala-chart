/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012-2013 Christian Krause                                                       *
 *                                                                                               *
 *  Christian Krause <kizkizzbangbang@googlemail.com>                                            *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of 'scala-chart'.                                                          *
 *                                                                                               *
 *  This project is free software: you can redistribute it and/or modify it under the terms      *
 *  of the GNU Lesser General Public License as published by the Free Software Foundation,       *
 *  either version 3 of the License, or any later version.                                       *
 *                                                                                               *
 *  This project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;    *
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    *
 *  See the GNU Lesser General Public License for more details.                                  *
 *                                                                                               *
 *  You should have received a copy of the GNU Lesser General Public License along with this     *
 *  project. If not, see <http://www.gnu.org/licenses/>.                                         *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package scalax.chart

import org.jfree.chart.plot.MultiplePiePlot

import Imports._

/** Represents categorized numeric data with multiple pies. */
trait MultiplePieChart extends Chart[MultiplePiePlot]
    with Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  def underlying: PieChart = {
    val u = plot.getPieChart
    new PieChart {
      override val peer = u
    }
  }

  override def plot: MultiplePiePlot = peer.getPlot.asInstanceOf[MultiplePiePlot]

  override def labelGenerator: Option[PieSectionLabelGenerator] = underlying.labelGenerator
  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]) {
    underlying.labelGenerator = generator
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = underlying.tooltipGenerator
  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]) {
    underlying.tooltipGenerator = generator
  }
}
