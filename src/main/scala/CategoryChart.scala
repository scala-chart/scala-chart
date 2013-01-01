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

import scala.swing.{ Orientable, Orientation }

import org.jfree.chart.labels._
import org.jfree.chart.plot._

/** Represents categorized numeric data. */
trait CategoryChart extends Chart[CategoryPlot] with Orientable with DomainAxis with RangeAxis
    with Labels[CategoryItemLabelGenerator]
    with Tooltips[CategoryToolTipGenerator] {

  override def plot: CategoryPlot = peer.getCategoryPlot

  override def domainAxisLabel: Option[String] = Option(plot.getDomainAxis.getLabel) filterNot { _ == "" }
  override def domainAxisLabel_=(label: Option[String]) {
    plot.getDomainAxis.setLabel(label.getOrElse(""))
  }

  override def labelGenerator: Option[CategoryItemLabelGenerator] = Option(plot.getRenderer.getBaseItemLabelGenerator)
  override def labelGenerator_=(generator: Option[CategoryItemLabelGenerator]) {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(generator.orNull)
  }

  override def orientation: Orientation.Value = plot.getOrientation
  override def orientation_=(orientation: Orientation.Value) {
    plot.setOrientation(orientation)
  }

  override def rangeAxisLabel: Option[String] = Option(plot.getRangeAxis.getLabel) filterNot { _ == "" }
  override def rangeAxisLabel_=(label: Option[String]) {
    plot.getRangeAxis.setLabel(label.getOrElse(""))
  }

  override def tooltipGenerator: Option[CategoryToolTipGenerator] = Option(plot.getRenderer.getBaseToolTipGenerator)
  override def tooltipGenerator_=(generator: Option[CategoryToolTipGenerator]) {
    plot.getRenderer.setBaseToolTipGenerator(generator.orNull)
  }

}
