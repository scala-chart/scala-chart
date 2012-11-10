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

import language.implicitConversions

import RichChartingCollections._

import org.jfree.data.time._
import org.jfree.data.xy._

/** $CollectionOfSeriesViewsInfo */
object CollectionOfSeriesViews extends CollectionOfSeriesViews

/** $CollectionOfSeriesViewsInfo
  *
  * @define CollectionOfSeriesViewsInfo Contains implicit views that convert collections of the
  * simpler `*Series` datasets to their `*SeriesCollection` counterparts so they can be used by
  * chart factories.
  */
trait CollectionOfSeriesViews {

  implicit def asTimePeriodValuesCollection(it: Iterable[TimePeriodValues]): TimePeriodValuesCollection =
    it.toTimePeriodValuesCollection

  implicit def asTimeSeriesCollection(it: Iterable[TimeSeries]): TimeSeriesCollection =
    it.toTimeSeriesCollection

  implicit def asXYSeriesCollection(it: Iterable[XYSeries]): XYSeriesCollection =
    it.toXYSeriesCollection

}
