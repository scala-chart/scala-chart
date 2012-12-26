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
import org.jfree.chart.plot.MultiplePiePlot
import org.jfree.data.category.CategoryDataset
import org.jfree.util.TableOrder

/** Factory for multiple pie charts. */
object MultiplePieChart extends ChartFactory {

  /** Creates a new multiple pie chart.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    */
  def apply(dataset: CategoryDataset,
            title: String = "",
            legend: Boolean = true,
            tooltips: Boolean = true): MultiplePieChart = {
    val chart = createMultiplePieChart(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)
    new MultiplePieChart {
      override val peer = chart
    }
  }

  /** Creates a new multiple pie chart with three dimensional pies.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    */
  def threeDimensional(dataset: CategoryDataset,
                       title: String = "",
                       legend: Boolean = true,
                       tooltips: Boolean = true): MultiplePieChart = {
    val chart = createMultiplePieChart3D(title, dataset, TableOrder.BY_COLUMN, legend, tooltips, false)
    new MultiplePieChart {
      override val peer = chart
    }
  }

}

/** Represents categorized numeric data with multiple pies. */
trait MultiplePieChart extends Chart[MultiplePiePlot] {
  override def plot: MultiplePiePlot = peer.getPlot.asInstanceOf[MultiplePiePlot]
}
