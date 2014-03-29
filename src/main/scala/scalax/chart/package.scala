package scalax

import language.implicitConversions

import scala.swing.Orientation
import org.jfree.chart.plot.PlotOrientation

/** This package contains a library for creating and working with charts. It wraps
  * [[http://www.jfree.org/jfreechart/ JFreeChart]], much like `scala.swing` does with the original
  * `javax.swing` package. The basic starting point is to use the following imports:
  *
  * {{{
  * import scalax.chart.api._
  * }}}
  *
  * With these imports you can convert Scala collections to the datasets of JFreeChart and use chart
  * factories:
  *
  * {{{
  * val data = Seq((1,2),(2,4),(3,6),(4,8))
  * val dataset = data.toXYSeriesCollection("some points")
  * val chart = XYLineChart(dataset, title = "My Chart of Some Points")
  * }}}
  *
  * == API Getting To Know Starting Points ==
  *
  * If you would like to get into this API you should start with the following members (generally,
  * these mix in a lot of others, so I recommended hitting '''Hide All''' and then iteratively
  * browse through the '''Inherited''' members):
  *
  *  - [[module.RichChartingCollections]] how to convert data to create charts
  *  - [[module.ChartFactories]] entry point for most chart factories
  *  - [[Chart]] as the base wrapper class
  *  - [[api]] as the base import
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
