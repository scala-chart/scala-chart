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


package scalax

import language.implicitConversions

import scala.swing.Orientation
import org.jfree.chart.plot.PlotOrientation

/** This package contains a library for creating and working with charts. It wraps
  * [[http://www.jfree.org/jfreechart/ JFreeChart]], much like `scala.swing` does with the original
  * `javax.swing` package. The basic starting point is to use the following imports:
  *
  * {{{
  * import scalax.chart._
  * import scalax.chart.Charting._
  * }}}
  *
  * With these imports you can convert Scala collections to the datasets of JFreeChart and use chart
  * factories:
  *
  * {{{
  * val data = Seq((1,2),(2,4),(3,6),(4,8))
  * val dataset = data.toXYSeriesCollection("some points")
  * val chart = XYLineChart(dataset, title = "My Chart of Some Points")
  * }}}
  *
  * There are also implicit conversions / views available in the [[scalax.chart.views]] package, but
  * they are not contained by [[scalax.chart.Charting]], because of ambiguity issues with implicit
  * conversions.
  */
package object chart {

  // -----------------------------------------------------------------------------------------------
  // more meaningful function aliases
  // -----------------------------------------------------------------------------------------------

  /** Function alias for creating item labels for [[scalax.chart.CategoryChart]]s. */
  type CategoryItemLabelGenerator = (org.jfree.data.category.CategoryDataset,Int,Int) ⇒ String

  /** Function alias for creating tooltips for [[scalax.chart.CategoryChart]]s. */
  type CategoryToolTipGenerator = (org.jfree.data.category.CategoryDataset,Int,Int) ⇒ String

  /** Function alias for creating item labels for [[scalax.chart.PieChartLike]] charts. */
  type PieSectionLabelGenerator = (org.jfree.data.general.PieDataset,Comparable[_]) ⇒ String

  /** Function alias for creating tooltips for [[scalax.chart.PieChartLike]] charts. */
  type PieToolTipGenerator = (org.jfree.data.general.PieDataset,Comparable[_]) ⇒ String

  /** Function alias for creating item labels for [[scalax.chart.XYChart]]s. */
  type XYItemLabelGenerator = (org.jfree.data.xy.XYDataset,Int,Int) ⇒ String

  /** Function alias for creating tooltips for [[scalax.chart.XYChart]]s. */
  type XYToolTipGenerator = (org.jfree.data.xy.XYDataset,Int,Int) ⇒ String

  // -----------------------------------------------------------------------------------------------
  // implicit conversions between scala-chart and JFreeChart
  // -----------------------------------------------------------------------------------------------

  private[chart] implicit def plotOrientation2orientation(o: PlotOrientation): Orientation.Value = o match {
    case PlotOrientation.HORIZONTAL ⇒ Orientation.Horizontal
    case PlotOrientation.VERTICAL   ⇒ Orientation.Vertical
  }

  private[chart] implicit def orientation2plotOrientation(o: Orientation.Value): PlotOrientation = o match {
    case Orientation.Horizontal ⇒ PlotOrientation.HORIZONTAL
    case Orientation.Vertical   ⇒ PlotOrientation.VERTICAL
  }

}
