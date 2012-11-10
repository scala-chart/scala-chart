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

import org.jfree.data.time._
import org.jfree.data.xy._

/** $SeriesViewsInfo */
object SeriesViews extends SeriesViews

/** $SeriesViewsInfo
  *
  * @define SeriesViewsInfo Contains implicit views that convert the simpler `*Series` datasets to
  * their `*SeriesCollection` counterparts so they can be used by chart factories.
  */
trait SeriesViews {

  implicit def asTimePeriodValuesCollection(tpvs: TimePeriodValues): TimePeriodValuesCollection =
    new TimePeriodValuesCollection(tpvs)

  implicit def asTimeSeriesCollection(ts: TimeSeries): TimeSeriesCollection =
    new TimeSeriesCollection(ts)

  implicit def asXYSeriesCollection(xys: XYSeries): XYSeriesCollection =
    new XYSeriesCollection(xys)

}
