package scalax.chart

import scala.collection.JavaConverters._
import scala.collection.Traversable
import scala.collection.mutable.Buffer

import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.Plot
import org.jfree.chart.title.Title

import Imports._

/** Generic graphical representation of data.
  *
  * @tparam P used plot type
  */
abstract class Chart[P <: Plot] protected () extends DisplayableChart with StorableChart {

  /** Returns the underlying chart. */
  def peer: JFreeChart

  /** Returns the plot. */
  def plot: P

  /** Returns true if this chart is drawn with anti-aliasing. */
  def antiAlias: Boolean =
    peer.getAntiAlias

  /** Sets whether or not this chart is drawn with anti-aliasing. */
  def antiAlias_=(a: Boolean): Unit =
    peer.setAntiAlias(a)

  /** Returns the paint used for the chart background. */
  def backgroundPaint: Paint =
    peer.getBackgroundPaint

  /** Sets the paint used for the chart background. */
  def backgroundPaint_=(p: Paint): Unit =
    peer.setBackgroundPaint(p)

  /** Returns the title of this chart. */
  def title: String =
    peer.getTitle.getText

  /** Sets the title of this chart. */
  def title_=(title: String): Unit =
    peer.setTitle(title)

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

    override def apply(n: Int): Title =
      peer.getSubtitle(n)

    override def clear(): Unit =
      peer.clearSubtitles()

    override def insertAll(n: Int, elems: Traversable[Title]): Unit = {
      var i = n
      elems foreach { title ⇒
        peer.addSubtitle(i, title)
        i += 1
      }
    }

    override def iterator: Iterator[Title] =
      peer.getSubtitles.iterator.asScala.map(_.asInstanceOf[Title])

    override def length: Int =
      peer.getSubtitleCount

    override def remove(n: Int): Title = {
      val title = apply(n)
      peer.removeSubtitle(title)
      title
    }

    override def update(n: Int, newTitle: Title): Unit = {
      remove(n)
      peer.addSubtitle(n, newTitle)
    }
  }

}
