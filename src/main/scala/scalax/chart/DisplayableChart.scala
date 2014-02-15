package scalax.chart

import scala.swing._
import scala.swing.Swing._

import org.jfree.chart._

/** @define DisplayableChartInfo This abstraction provides methods for integration with the
  * [[http://www.scala-lang.org/api/current/index.html#scala.swing.package Scala Swing UI
  * framework]].
  *
  * {{{
  * val component = chart.toComponent
  *
  * val container = new FlowPanel
  * container.contents += component
  *
  * val frame = chart.toFrame
  * }}}
  *
  * There are also methods to immediately display the chart in a new GUI window. This is intended
  * for interactive usage, e.g. when working with the Scala REPL. The simplest form is:
  *
  * {{{
  * chart.show
  * }}}
  *
  * @define title      the title of the enclosing frame
  * @define scrollable whether the enclosing panel is scrollable
  */
private[chart] trait DisplayableChart {

  chart: Chart[_] ⇒

  /** Shows the chart in a window. */
  def show: Unit = show()

  /** Shows the chart in a window.
    *
    * @param title      $title
    * @param scrollable $scrollable
    */
  def show(title: String = "", dim: (Int,Int) = Chart.Default.Resolution, scrollable: Boolean = false): Unit = Swing onEDT {
    val frame = toFrame(title, scrollable)
    frame.size = dim
    frame.visible = true
  }

  /** Wraps this chart in a swing component. */
  def toComponent: Component =
    Component wrap new ChartPanel(chart.peer)

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
    new Frame {
      override lazy val peer = new ChartFrame(t, chart.peer, scrollable) with InterfaceMixin
    }
  }

}
