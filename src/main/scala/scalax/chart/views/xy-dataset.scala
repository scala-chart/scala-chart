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
package views

import language.implicitConversions

import org.jfree.data.xy._

import RichChartingCollections._

// -------------------------------------------------------------------------------------------------
// conversion from *Series to *SeriesCollection
// -------------------------------------------------------------------------------------------------

object XYSeriesViews extends XYSeriesViews
trait XYSeriesViews {
  implicit def asXYSeriesCollection(xys: XYSeries): XYSeriesCollection =
    new XYSeriesCollection(xys)
}

// -------------------------------------------------------------------------------------------------
// conversion from collection of *Series to *SeriesCollection
// -------------------------------------------------------------------------------------------------

object CollectionOfXYSeriesViews extends CollectionOfXYSeriesViews
trait CollectionOfXYSeriesViews {
  implicit def asXYSeriesCollection(it: Iterable[XYSeries]): XYSeriesCollection =
    it.toXYSeriesCollection
}

// -------------------------------------------------------------------------------------------------
// conversion from scala.collection to datasets
// -------------------------------------------------------------------------------------------------

object CollectionToXYSeriesViews extends CollectionToXYSeriesViews
trait CollectionToXYSeriesViews {
  implicit def asXYSeries[A <% Number, B <% Number](it: Iterable[(A,B)]): XYSeries =
    it.toXYSeries()
  implicit def asXYSeriesCollection[A <% Number, B <% Number](it: Iterable[(A,B)]): XYSeriesCollection =
    it.toXYSeriesCollection()
}

// -------------------------------------------------------------------------------------------------
// import containing all of the above
// -------------------------------------------------------------------------------------------------

object XYDatasetViews extends XYDatasetViews
trait XYDatasetViews extends CollectionToXYSeriesViews with XYSeriesViews
  with CollectionOfXYSeriesViews
