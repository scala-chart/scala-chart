package scalax.chart

import event._

import scala.swing._
import scala.swing.event.{ MouseClicked, MouseMoved }
import scala.swing.Swing._

import org.jfree.{ chart => jchart }

/** @define DisplayableChartInfo This abstraction provides direct integration with the
  * [[http://www.scala-lang.org/api/current/index.html#scala.swing.package Scala Swing UI
  * framework]].
  *
  * {{{
  * val component = chart.toComponent
  * val frame = chart.toFrame()
  * }}}
  *
  * There is also a way to immediately display the chart in a new GUI window. This is intended for
  * interactive usage, e.g. when working with the Scala REPL. The simplest form is:
  *
  * {{{
  * chart.show()
  * }}}
  *
  * Listen to UI mouse events:
  *
  * {{{
  * import scalax.chart.event._
  *
  * val chart = ???
  * val ui = chart.toComponent // or chart.toFrame()
  * val reactor = ???
  *
  * reactor.listenTo(ui)
  * reactor.reactions += {
  *   case event: ChartMouseEvent =>
  *     // do something with event
  * }
  * }}}
  *
  * @define title      the title of the enclosing frame
  * @define scrollable whether the enclosing panel is scrollable
  */
private[chart] trait DisplayableChart {

  chart: Chart =>

  /** Shows the chart in a window.
    *
    * @param title      $title
    * @param scrollable $scrollable
    *
    * @usecase def show(): Unit
    *   @inheritdoc
    */
  def show(title: String = "", dim: (Int,Int) = Chart.Default.Resolution, scrollable: Boolean = false): Unit = Swing onEDT {
    val frame = toFrame(title, scrollable)
    frame.size = dim
    frame.visible = true
  }

  /** Wraps this chart in a swing component. */
  def toComponent: Component = {
    val peer = new jchart.ChartPanel(chart.peer)
    val wrapped = Component.wrap(peer)
    applyScalaSwingListenerTo(peer, source = wrapped, publisher = wrapped)
    wrapped
  }

  /** Wraps a frame around this chart.
    *
    * @param title      $title
    * @param scrollable $scrollable
    *
    * @usecase def toFrame(): Frame
    *   @inheritdoc
    */
  def toFrame(title: String = "", scrollable: Boolean = true): Frame = {
    val t = title
    val frame = new Frame {
      override lazy val peer = new jchart.ChartFrame(t, chart.peer, scrollable) with InterfaceMixin
    }

    applyScalaSwingListenerTo(frame.peer.getChartPanel, frame, frame)

    frame
  }

  private def applyScalaSwingListenerTo(chartPanel: jchart.ChartPanel, source: UIElement, publisher: Publisher) = {
    chartPanel.addChartMouseListener(new jchart.ChartMouseListener {
      override final def chartMouseClicked(event: jchart.ChartMouseEvent): Unit =
        publisher.publish(ChartMouseClicked(new MouseClicked(event.getTrigger), Option(event.getEntity)))
      override final def chartMouseMoved(event: jchart.ChartMouseEvent): Unit =
        publisher.publish(ChartMouseMoved(new MouseMoved(event.getTrigger), Option(event.getEntity)))
    })
  }

}
