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

/** $ImportsInfo */
object Imports extends Imports

/** $TypeImportsInfo */
object TypeImports extends TypeImports

/** $StaticForwarderImportsInfo */
object StaticForwarderImports extends StaticForwarderImports

/** $ImportsInfo
  *
  * @define ImportsInfo Contains imports from foreign packages.
  */
trait Imports extends TypeImports with StaticForwarderImports

/** $TypeImportsInfo
  *
  * @define TypeImportsInfo Contains only the type imports from foreign packages.
  */
trait TypeImports {
  type Orientation = scala.swing.Orientation.Value

  type CategoryDataset = org.jfree.data.category.CategoryDataset
  type PieDataset = org.jfree.data.general.PieDataset
  type XYDataset = org.jfree.data.xy.XYDataset

  type CategoryPlot = org.jfree.chart.plot.CategoryPlot
  type MultiplePiePlot = org.jfree.chart.plot.MultiplePiePlot
  type PiePlot = org.jfree.chart.plot.PiePlot
  type XYPlot = org.jfree.chart.plot.XYPlot
}

/** $StaticForwarderImportsInfo
  *
  * @define StaticForwarderImportsInfo Contains only the static forwarder imports from foreign packages.
  */
trait StaticForwarderImports {
  val Orientation = scala.swing.Orientation
}
