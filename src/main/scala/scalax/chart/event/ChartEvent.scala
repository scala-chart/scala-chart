package scalax.chart
package event

import scala.swing.event.Event

import org.jfree.chart.plot.Plot
import org.jfree.chart.title.Title

/** Event on a chart.
  *
  * @see [[Chart]]
  */
sealed abstract class ChartEvent protected () extends Event {

  /** Returns the chart on which the event occurred. */
  def chart: Chart

}

/** Contains concrete chart events. */
object ChartEvent {

  /** Event indicating a dataset has been updated. */
  final case class DatasetUpdated(chart: Chart) extends ChartEvent

  /** Event indicating a new dataset has been added to the chart. */
  final case class NewDataset(chart: Chart) extends ChartEvent

  /** Event indicating a click on a chart. */
  final case class General(chart: Chart) extends ChartEvent

}

/** Event indicating a plot of the chart has changed. */
final case class PlotChanged(chart: Chart, plot: Plot) extends ChartEvent

/** Event indicating a title of the chart has changed. */
final case class TitleChanged(chart: Chart, title: Title) extends ChartEvent
