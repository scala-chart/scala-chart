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

import org.jfree.chart.ChartFactory._
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.PieDataset

/** Factory for pie charts. */
object PieChart extends ChartFactory {

  /** Creates a new pie chart.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    */
  def apply(dataset: PieDataset,
            title: String = "",
            legend: Boolean = true,
            tooltips: Boolean = true): PieChart = {
    val chart = createPieChart(title, dataset, legend, tooltips, false)
    new PieChart {
      override val peer = chart
    }
  }

  /** Creates a new pie chart with a three dimensional pie.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    */
  def threeDimensional(dataset: PieDataset,
                       title: String = "",
                       legend: Boolean = true,
                       tooltips: Boolean = true): PieChart = {
    val chart = createPieChart3D(title, dataset, legend, tooltips, false)
    new PieChart {
      override val peer = chart
    }
  }

}

/** Represents categorized numeric data with a pie. */
trait PieChart extends Chart[PiePlot] with PieChartLike[PiePlot] {
  override def plot: PiePlot = peer.getPlot.asInstanceOf[PiePlot]
}
