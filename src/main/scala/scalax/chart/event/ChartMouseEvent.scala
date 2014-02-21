package scalax.chart
package event

import java.awt.event.MouseEvent

import org.jfree.chart.entity.ChartEntity

import scala.swing.UIElement
import scala.swing.event.UIEvent

/** Mouse event on a chart UI element.
  *
  * @see [[Chart]]
  */
sealed abstract class ChartMouseEvent extends UIEvent {

  /** Returns the low level mouse event which triggered this event. */
  def trigger: MouseEvent

  /** Optionally returns the chart entity under the mouse point. */
  def entity: Option[ChartEntity]

}

/** Extractor companion. */
object ChartMouseEvent {

  /** Returns the members of a chart mouse event. */
  def unapply(event: ChartMouseEvent): Option[(UIElement,MouseEvent,Option[ChartEntity])] =
    Some((event.source, event.trigger, event.entity))

}

/** Event indicating a click on a chart. */
final case class ChartMouseClicked(source: UIElement, trigger: MouseEvent, entity: Option[ChartEntity]) extends ChartMouseEvent

/** Event indicating mouse movement over a chart. */
final case class ChartMouseMoved(source: UIElement, trigger: MouseEvent, entity: Option[ChartEntity]) extends ChartMouseEvent
