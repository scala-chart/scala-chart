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

import scala.collection.Traversable
import scala.collection.mutable.Buffer

import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.Plot
import org.jfree.chart.title.Title

/** Generic graphical representation of data.
  *
  * @tparam P used plot type
  */
trait Chart[P <: Plot] extends DisplayableChart with StorableChart {

  /** Returns the underlying chart. */
  def peer: JFreeChart

  /** Returns the plot. */
  def plot: P

  /** Contains this charts subtitles and legends. */
  object subtitles extends Buffer[Title] {
    override def +=(title: Title): this.type = {
      peer.addSubtitle(title)
      this
    }

    override def +=:(title: Title): this.type = {
      peer.addSubtitle(0, title)
      this
    }

    override def apply(n: Int): Title = peer.getSubtitle(n)

    override def clear() {
      peer.clearSubtitles()
    }

    override def insertAll(n: Int, elems: Traversable[Title]) {
      var i = n
      elems. foreach { title ⇒
        peer.addSubtitle(i, title)
        i += 1
      }
    }

    override def iterator: Iterator[Title] = new Iterator[Title] {
      private var current = 0
      override def hasNext: Boolean = subtitles.size > current
      override def next: Title = {
        val subtitle = subtitles(current)
        current += 1
        subtitle
      }
    }

    override def length: Int = peer.getSubtitleCount

    override def remove(n: Int): Title = {
      val title = apply(n)
      peer.removeSubtitle(title)
      title
    }

    override def update(n: Int, newTitle: Title) {
      val oldTitle = apply(n)
      remove(n)
      peer.addSubtitle(n, newTitle)
    }
  }

  /** Returns the title of this chart. */
  def title: String = peer.getTitle.getText

  /** Sets the title of this chart. */
  def title_=(title: String): Unit = peer.setTitle(title)

}
