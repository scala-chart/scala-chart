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

import org.jfree.data.category._
import org.jfree.data.time._
import org.jfree.data.xy._

import RichChartingCollections._

object DatasetViews extends DatasetViews

trait DatasetViews {

  implicit def asXYSeries[A <% Number, B <% Number](it: Iterable[(A,B)]): XYSeries =
    it.toXYSeries()

  implicit def asXYSeriesCollection[A <% Number, B <% Number](it: Iterable[(A,B)]): XYSeriesCollection =
    it.toXYSeriesCollection()

  implicit def asTimeSeries[A <% RegularTimePeriod, B <% Number](it: Iterable[(A,B)]): TimeSeries =
    it.toTimeSeries()

  implicit def asTimeSeriesCollection[A <% RegularTimePeriod, B <% Number](it: Iterable[(A,B)]): TimeSeriesCollection =
    it.toTimeSeriesCollection()

  implicit def asTimePeriodValues[A <% TimePeriod, B <% Number](it: Iterable[(A,B)]): TimePeriodValues =
    it.toTimePeriodValues()

  implicit def asTimePeriodValuesCollection[A <% TimePeriod, B <% Number](it: Iterable[(A,B)]): TimePeriodValuesCollection =
    it.toTimePeriodValuesCollection()

  implicit def asTimeTable[A <% Comparable[A], B <% TimePeriod, C <% Number](it: Iterable[(A,Iterable[(B,C)])]): TimeTableXYDataset =
    it.toTimeTable

  implicit def asCategoryDataset2[A <% Comparable[A], B <% Number](it: Iterable[(A,B)]): CategoryDataset =
    it.toCategoryDataset

  implicit def asCategoryDataset3[A <% Comparable[A], B <% Comparable[B], C <% Number](it: Iterable[(A,Iterable[(B,C)])]): CategoryDataset =
    it.toCategoryDataset

}
