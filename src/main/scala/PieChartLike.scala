/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012 Christian Krause                                                            *
 *                                                                                               *
 *  Christian Krause <kizkizzbangbang@googlemail.com>                                            *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of 'sfreechart'.                                                           *
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


package org.sfree.chart

import org.jfree.chart.labels._
import org.jfree.chart.plot.PiePlot

/** Template trait for pie charts. */
trait PieChartLike[P <: PiePlot]
    extends Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  self: Chart[P] ⇒

  override def labelGenerator: Option[PieSectionLabelGenerator] = Option(plot.getLabelGenerator)
  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]) {
    plot.setLabelGenerator(generator.orNull)
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = Option(plot.getToolTipGenerator)
  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]) {
    plot.setToolTipGenerator(generator.orNull)
  }
}
