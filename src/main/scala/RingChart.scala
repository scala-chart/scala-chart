/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012-2013 Christian Krause                                                       *
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

import org.jfree.chart.ChartFactory.createRingChart
import org.jfree.chart.plot.RingPlot
import org.jfree.data.general.PieDataset

/** Factory for ring charts. */
object RingChart extends ChartFactory {

  /** Creates a new ring chart.
    *
    * @param dataset  $dataset
    * @param title    $title
    * @param legend   $legend
    * @param tooltips $tooltips
    */
  def apply(dataset: PieDataset,
            title: String = "",
            legend: Boolean = true,
            tooltips: Boolean = true): RingChart = {
    val chart = createRingChart(title, dataset, legend, tooltips, false)
    new RingChart {
      override val peer = chart
    }
  }

}

/** Represents categorized numeric data with a ring. */
trait RingChart extends Chart[RingPlot] with PieChartLike[RingPlot] {
  override def plot: RingPlot = peer.getPlot.asInstanceOf[RingPlot]
}
