package scalax.chart
package event

import org.jfree.chart.entity.ChartEntity

import scala.swing.event._

/** Mouse event on a chart UI element.
  *
  * @see [[Chart]]
  */
sealed abstract class ChartMouseEvent extends Event {

  /** Returns the mouse event which triggered this event. */
  def trigger: MouseEvent

  /** Optionally returns the chart entity under the mouse point. */
  def entity: Option[ChartEntity]

}

/** Event indicating a click on a chart. */
final case class ChartMouseClicked(trigger: MouseClicked, entity: Option[ChartEntity]) extends ChartMouseEvent

/** Event indicating mouse movement over a chart. */
final case class ChartMouseMoved(trigger: MouseMoved, entity: Option[ChartEntity]) extends ChartMouseEvent
