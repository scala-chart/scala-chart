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


package org.sfree

/** This package contains wrappers for the [[http://www.jfree.org/jfreechart/ JFreeChart]] library.
  *
  * == Imports ==
  *
  * Imports are provided in groups:
  *
  * [[org.sfree.chart.Charting]] contains all enrichments. This import is the recommended starting
  * point for working with sfreechart:
  *
  * {{{
  * import org.sfree.chart.Charting._
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = LineChart(data)
  * }}}
  *
  * [[org.sfree.chart.Views]] contains all implicit views / conversions. Use this import with care,
  * because of ambiguous implicit conversions. It is better to just import the specific conversion
  * you need:
  *
  * {{{
  * import org.sfree.chart.Charting._
  * import org.sfree.chart.Views.asXYSeriesCollection
  * val data = Seq((0,0),(1,1),(2,2))
  * val chart = LineChart(data)
  * }}}
  */
package object chart {
}
