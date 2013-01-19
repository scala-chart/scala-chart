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

import scala.swing._

import org.jfree.chart._

/** Provides methods for displaying a chart.
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
  def show(title: String = "", scrollable: Boolean = true): Unit = Swing onEDT {
    toFrame(title, scrollable).visible = true
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
