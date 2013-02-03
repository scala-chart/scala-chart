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

import Imports._

/** Template trait for pie charts. */
trait PieChartLike[P <: PiePlot]
    extends Labels[PieSectionLabelGenerator]
    with Tooltips[PieToolTipGenerator] {

  self: Chart[P] ⇒

  override def labelGenerator: Option[PieSectionLabelGenerator] = Option (
    plot.getLabelGenerator
  ) map { _.generateSectionLabel _ }

  override def labelGenerator_=(generator: Option[PieSectionLabelGenerator]) {
    plot.setLabelGenerator(generator.map( lg ⇒
      new org.jfree.chart.labels.PieSectionLabelGenerator {
        override def generateAttributedSectionLabel(dataset: PieDataset, key: Comparable[_]): java.text.AttributedString =
          null
        override def generateSectionLabel(dataset: PieDataset, key: Comparable[_]): String =
          lg(dataset, key)
      }
    ).orNull)
  }

  override def tooltipGenerator: Option[PieToolTipGenerator] = Option (
    plot.getToolTipGenerator
  ) map { _.generateToolTip _ }

  override def tooltipGenerator_=(generator: Option[PieToolTipGenerator]) {
    plot.setToolTipGenerator(generator.map( ttg ⇒
      new org.jfree.chart.labels.PieToolTipGenerator {
        override def generateToolTip(dataset: PieDataset, key: Comparable[_]): String =
          ttg(dataset, key)
      }
    ).orNull)
  }
}
