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
import org.jfree.chart.plot._

/** Represents numeric data. */
trait XYChart extends Chart[XYPlot] with Orientable with DomainAxis with RangeAxis
    with Labels[XYItemLabelGenerator]
    with Tooltips[XYToolTipGenerator] {

  override def plot: XYPlot = peer.getXYPlot

  override def domainAxisLabel: String = plot.getDomainAxis.getLabel
  override def domainAxisLabel_=(label: String) {
    plot.getDomainAxis.setLabel(label)
  }

  override def labelGenerator: Option[XYItemLabelGenerator] = Option(plot.getRenderer.getBaseItemLabelGenerator)
  override def labelGenerator_=(generator: Option[XYItemLabelGenerator]) {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(generator.orNull)
  }

  override def orientation: PlotOrientation = plot.getOrientation
  override def orientation_=(orientation: PlotOrientation) {
    plot.setOrientation(orientation)
  }

  override def rangeAxisLabel: String = plot.getRangeAxis.getLabel
  override def rangeAxisLabel_=(label: String) {
    plot.getRangeAxis.setLabel(label)
  }

  override def tooltipGenerator: Option[XYToolTipGenerator] = Option(plot.getRenderer.getBaseToolTipGenerator)
  override def tooltipGenerator_=(generator: Option[XYToolTipGenerator]) {
    plot.getRenderer.setBaseToolTipGenerator(generator.orNull)
  }

}
