package scalax

import language.implicitConversions

import scala.swing.Orientation
import org.jfree.chart.plot.PlotOrientation

/** This package contains a library for creating and working with charts. It wraps
  * [[http://www.jfree.org/jfreechart/ JFreeChart]], much like `scala.swing` does with the original
  * `javax.swing` package.
  *
  * == Getting Started ==
  *
  * There is an all-you-can-eat import providing all the high-level functionality of this library:
  *
  * {{{
  * import scalax.chart.api._
  *
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = XYLineChart(data)
  * chart.saveAsPNG("/tmp/chart.png")
  * }}}
  *
  * All of The functionality of the [[api]] object is also contained by [[module.Charting]], which
  * you can either import or use as a mixin:
  *
  * {{{
  * object MyChartApp extends App with scalax.chart.module.Charting {
  *   val data = for (i <- 1 to 5) yield (i,i)
  *   val chart = XYLineChart(data)
  *   chart.saveAsPNG("/tmp/chart.png")
  * }
  * }}}
  *
  * The [[module]] package provides a la carte imports, which you can import or mix in for only
  * parts of the API. To find out more about the modules in detail, have a look at the documentation
  * of the [[module.Charting]] module. From there on you can discover the modules one by one.
  */
package object chart {

  private[chart] implicit def plotOrientation2orientation(o: PlotOrientation): Orientation.Value = o match {
    case PlotOrientation.HORIZONTAL ⇒ Orientation.Horizontal
    case PlotOrientation.VERTICAL   ⇒ Orientation.Vertical
  }

  private[chart] implicit def orientation2plotOrientation(o: Orientation.Value): PlotOrientation = o match {
    case Orientation.Horizontal ⇒ PlotOrientation.HORIZONTAL
    case Orientation.Vertical   ⇒ PlotOrientation.VERTICAL
  }

  private[chart] implicit class ToDatasetOps[A](underlying: A) {
    def toDataset(implicit converter: ToDataset[A]): converter.X =
      converter.toDataset(underlying)
  }

}
