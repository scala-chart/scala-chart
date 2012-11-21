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

/** This package contains implicit views / implicit conversions for SFreeChart. There are different
  * groups of imports available for specific purposes to avoid ambiguous implicit conversions.
  *
  * == CollectionTo*Views ==
  *
  * These contain views that convert ordinary Scala collections to datasets.
  *
  * {{{
  * import org.sfree.chart.Charting._
  * import org.sfree.chart.views.CollectionToXYSeriesViews._
  *
  * val data = Seq((1,2),(2,4),(3,6),(4,8))
  * val chart = LineChart(data, title = "My Chart of Some Points")
  * }}}
  *
  * == SeriesViews ==
  *
  * [[org.sfree.chart.views.SeriesViews]] contains views that convert the simpler `*Series` datasets
  * to their `*SeriesCollection` counterparts so they can be used by chart factories:
  *
  * {{{
  * import org.sfree.chart.Charting._
  * import org.sfree.chart.views.SeriesViews._
  *
  * val dataset = Seq((1,2),(2,4),(3,6),(4,8)).toXYSeries("some points")
  * val chart = LineChart(dataset, title = "My Chart of Some Points")
  * }}}
  *
  * == CollectionOfSeriesViews ==
  *
  * [[org.sfree.chart.views.CollectionOfSeriesViews]] contains views that convert Scala collections
  * of the simpler `*Series` datasets to their `*SeriesCollection` counterparts so they can be used
  * by chart factories:
  *
  * {{{
  * import org.sfree.chart.Charting._
  * import org.sfree.chart.views.CollectionOfSeriesViews._
  *
  * val dataset1 = Seq((1,2),(2,4),(3,6),(4,8)).toXYSeries("some points")
  * val dataset2 = Seq((1,3),(2,6),(3,9),(4,12)).toXYSeries("some other points")
  * val datasets = Seq(dataset1, dataset2)
  * val chart = LineChart(datasets, title = "My Chart of Some Points")
  * }}}
  *
  * == *DatasetViews ==
  *
  * These contain all views concerning a specific type of dataset.
  *
  * {{{
  * import org.sfree.chart.Charting._
  * import org.sfree.chart.views.XYDatasetViews._
  *
  * val data = Seq((1,2),(2,4),(3,6),(4,8))
  * val chart = LineChart(data, title = "My Chart of Some Points")
  * }}}
  */
package object views {
}
