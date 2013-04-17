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

import scala.swing._
import scala.swing.Swing._

import org.jfree.chart._
import org.jfree.chart.ChartPanel.{ DEFAULT_WIDTH, DEFAULT_HEIGHT }

/** Provides methods for both displaying a chart and integrating it into the
  * [[http://www.scala-lang.org/api/current/index.html#scala.swing.package Scala Swing UI
  * framework]].
  *
  * @define title      the title of the enclosing frame
  * @define scrollable whether the enclosing panel is scrollable
  */
trait DisplayableChart {

  chart: Chart[_] ⇒

  /** Shows the chart in a window. */
  def show: Unit = show()

  /** Shows the chart in a window.
    *
    * @param title      $title
    * @param scrollable $scrollable
    */
  def show(title: String = "", dim: (Int,Int) = (DEFAULT_WIDTH,DEFAULT_HEIGHT), scrollable: Boolean = false): Unit = Swing onEDT {
    val frame = toFrame(title, scrollable)
    frame.size = dim
    frame.visible = true
  }

  /** Wraps a panel around this chart. */
  def toPanel: Panel = new FlowPanel {
    override lazy val peer = new ChartPanel(chart.peer)
  }

  /** Wraps a frame around this chart.
    *
    * @param title      $title
    * @param scrollable $scrollable
    */
  def toFrame(title: String = "", scrollable: Boolean = true): Frame = {
    val t = title
    new Frame {
      override lazy val peer = new ChartFrame(t, chart.peer, scrollable) with InterfaceMixin
    }
  }

}
