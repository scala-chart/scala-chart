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
package views

import language.implicitConversions

import org.jfree.data.time._

import RichChartingCollections._

// -------------------------------------------------------------------------------------------------
// conversion from *Series to *SeriesCollection
// -------------------------------------------------------------------------------------------------

object TimeSeriesViews extends TimeSeriesViews
trait TimeSeriesViews {
  implicit def asTimeSeriesCollection(ts: TimeSeries): TimeSeriesCollection =
    new TimeSeriesCollection(ts)
}

object TimePeriodValuesViews extends TimePeriodValuesViews
trait TimePeriodValuesViews {
  implicit def asTimePeriodValuesCollection(tpvs: TimePeriodValues): TimePeriodValuesCollection =
    new TimePeriodValuesCollection(tpvs)
}

// -------------------------------------------------------------------------------------------------
// conversion from collection of *Series to *SeriesCollection
// -------------------------------------------------------------------------------------------------

object CollectionOfTimeSeriesViews extends CollectionOfTimeSeriesViews
trait CollectionOfTimeSeriesViews {
  implicit def asTimeSeriesCollection(it: Iterable[TimeSeries]): TimeSeriesCollection =
    it.toTimeSeriesCollection
}

object CollectionOfTimePeriodValuesViews extends CollectionOfTimePeriodValuesViews
trait CollectionOfTimePeriodValuesViews {
  implicit def asTimePeriodValuesCollection(it: Iterable[TimePeriodValues]): TimePeriodValuesCollection =
    it.toTimePeriodValuesCollection
}

// -------------------------------------------------------------------------------------------------
// conversion from scala.collection to datasets
// -------------------------------------------------------------------------------------------------

object CollectionToTimeSeriesViews extends CollectionToTimeSeriesViews
trait CollectionToTimeSeriesViews {
  implicit def asTimeSeries[A <% RegularTimePeriod, B <% Number](it: Iterable[(A,B)]): TimeSeries =
    it.toTimeSeries()
  implicit def asTimeSeriesCollection[A <% RegularTimePeriod, B <% Number](it: Iterable[(A,B)]): TimeSeriesCollection =
    it.toTimeSeriesCollection()
}

object CollectionToTimePeriodValuesViews extends CollectionToTimePeriodValuesViews
trait CollectionToTimePeriodValuesViews {
  implicit def asTimePeriodValues[A <% TimePeriod, B <% Number](it: Iterable[(A,B)]): TimePeriodValues =
    it.toTimePeriodValues()
  implicit def asTimePeriodValuesCollection[A <% TimePeriod, B <% Number](it: Iterable[(A,B)]): TimePeriodValuesCollection =
    it.toTimePeriodValuesCollection()
}

object CollectionToTimeTableViews extends CollectionToTimeTableViews
trait CollectionToTimeTableViews {
  implicit def asTimeTable[A <% Comparable[A], B <% TimePeriod, C <% Number](it: Iterable[(A,Iterable[(B,C)])]): TimeTableXYDataset =
    it.toTimeTable
}

// -------------------------------------------------------------------------------------------------
// imports containing the specific groups of the above
// -------------------------------------------------------------------------------------------------

object TimeDatasetViews extends TimeDatasetViews
trait TimeDatasetViews extends CollectionToTimeSeriesViews with TimeSeriesViews
  with CollectionOfTimeSeriesViews

object TimePeriodDatasetViews extends TimePeriodDatasetViews
trait TimePeriodDatasetViews extends CollectionToTimePeriodValuesViews with TimePeriodValuesViews
  with CollectionOfTimePeriodValuesViews

object TimeTableDatasetViews extends TimeTableDatasetViews
trait TimeTableDatasetViews extends CollectionToTimeTableViews
