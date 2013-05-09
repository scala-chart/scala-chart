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

import scala.swing.Orientable

import Imports._

/** Represents categorized numeric data. These charts have a domain axis that consists of the
  * categories and a numeric range axis.
  */
trait CategoryChart extends Chart[CategoryPlot] with Orientable with DomainAxis with RangeAxis
    with Labels[CategoryItemLabelGenerator]
    with Tooltips[CategoryToolTipGenerator] {

  override def plot: CategoryPlot = peer.getCategoryPlot

  override def domainAxisLabel: Option[String] = Option(plot.getDomainAxis.getLabel) filterNot { _ == "" }
  override def domainAxisLabel_=(label: Option[String]) {
    plot.getDomainAxis.setLabel(label.getOrElse(""))
  }

  override def labelGenerator: Option[CategoryItemLabelGenerator] = Option (
    plot.getRenderer.getBaseItemLabelGenerator
  ) map { _.generateLabel _ }

  override def labelGenerator_=(generator: Option[CategoryItemLabelGenerator]) {
    val renderer = plot.getRenderer
    renderer.setBaseItemLabelsVisible(generator.isDefined)
    renderer.setBaseItemLabelGenerator(generator.map( gen ⇒
      new org.jfree.chart.labels.CategoryItemLabelGenerator {
        override def generateColumnLabel(dataset: CategoryDataset, col: Int): String = ""
        override def generateLabel(dataset: CategoryDataset, row: Int, col: Int): String =
          gen(dataset, row, col)
        override def generateRowLabel(dataset: CategoryDataset, row: Int): String = ""
      }
    ).orNull)
  }

  override def orientation: Orientation = plot.getOrientation
  override def orientation_=(orientation: Orientation) {
    plot.setOrientation(orientation)
  }

  override def rangeAxisLabel: Option[String] = Option(plot.getRangeAxis.getLabel) filterNot { _ == "" }
  override def rangeAxisLabel_=(label: Option[String]) {
    plot.getRangeAxis.setLabel(label.getOrElse(""))
  }

  override def tooltipGenerator: Option[CategoryToolTipGenerator] = Option (
    plot.getRenderer.getBaseToolTipGenerator
  ) map { _.generateToolTip _ }

  override def tooltipGenerator_=(generator: Option[CategoryToolTipGenerator]) {
    plot.getRenderer.setBaseToolTipGenerator(generator.map( ttg ⇒
      new org.jfree.chart.labels.CategoryToolTipGenerator {
        override def generateToolTip(dataset: CategoryDataset, row: Int, col: Int): String =
          ttg(dataset, row, col)
      }
    ).orNull)
  }

}
