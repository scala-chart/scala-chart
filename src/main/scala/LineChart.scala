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

import scala.swing.Orientation

import org.jfree.chart.ChartFactory._
import org.jfree.data.category.CategoryDataset

/** Factory for line charts. */
object LineChart extends ChartFactory {

  /** Creates a new chart that represents categorized numeric values with a line.
    *
    * @param dataset         $dataset
    * @param title           $title
    * @param domainAxisLabel $domainAxisLabel
    * @param rangeAxisLabel  $rangeAxisLabel
    * @param orientation     $orientation
    * @param legend          $legend
    * @param tooltips        $tooltips
    */
  def apply(dataset: CategoryDataset,
            title: String = "",
            domainAxisLabel: String = "",
            rangeAxisLabel: String = "",
            orientation: Orientation.Value = Orientation.Vertical,
            legend: Boolean = true,
            tooltips: Boolean = false): CategoryChart = {
    val chart = createLineChart(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
      legend, tooltips, false)

    new CategoryChart {
      override val peer = chart
    }
  }

  /** Creates a new chart that represents categorized numeric values with three dimensional a
    * line.
    *
    * @param dataset         $dataset
    * @param title           $title
    * @param domainAxisLabel $domainAxisLabel
    * @param rangeAxisLabel  $rangeAxisLabel
    * @param orientation     $orientation
    * @param legend          $legend
    * @param tooltips        $tooltips
    */
  def threeDimensional(dataset: CategoryDataset,
                       title: String = "",
                       domainAxisLabel: String = "",
                       rangeAxisLabel: String = "",
                       orientation: Orientation.Value = Orientation.Vertical,
                       legend: Boolean = true,
                       tooltips: Boolean = false): CategoryChart = {
    val chart = createLineChart3D(title, domainAxisLabel, rangeAxisLabel, dataset, orientation,
      legend, tooltips, false)

    new CategoryChart {
      override val peer = chart
    }
  }

}
