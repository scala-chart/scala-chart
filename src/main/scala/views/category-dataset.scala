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
package views

import language.implicitConversions

import org.jfree.data.category._

import RichChartingCollections._

// -------------------------------------------------------------------------------------------------
// conversion from scala.collection to datasets
// -------------------------------------------------------------------------------------------------

object CollectionToCategoryDatasetViews extends CollectionToCategoryDatasetViews
trait CollectionToCategoryDatasetViews {
  implicit def asCategoryDataset2[A <% Comparable[A], B <% Number](it: Iterable[(A,B)]): CategoryDataset =
    it.toCategoryDataset
  implicit def asCategoryDataset3[A <% Comparable[A], B <% Comparable[B], C <% Number](it: Iterable[(A,Iterable[(B,C)])]): CategoryDataset =
    it.toCategoryDataset
}

// -------------------------------------------------------------------------------------------------
// import containing all of the above
// -------------------------------------------------------------------------------------------------

object CategoryDatasetViews extends CategoryDatasetViews
trait CategoryDatasetViews extends CollectionToCategoryDatasetViews
