package scalax

import language.implicitConversions

import scala.swing.Orientation
import org.jfree.chart.plot.PlotOrientation

/** This package contains a library for creating and working with charts. It wraps
  * [[http://www.jfree.org/jfreechart/ JFreeChart]], much like `scala.swing` does with the original
  * `javax.swing` package. The basic starting point is to use the following imports:
  *
  * {{{
  * import scalax.chart._
  * import scalax.chart.Charting._
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
  */
package object chart {

  // -----------------------------------------------------------------------------------------------
  // more meaningful function aliases
  // -----------------------------------------------------------------------------------------------

  /** Function alias for creating item labels for [[CategoryChart]]s. */
  type CategoryItemLabelGenerator = (org.jfree.data.category.CategoryDataset,Int,Int) ⇒ String

  /** Function alias for creating tooltips for [[CategoryChart]]s. */
  type CategoryToolTipGenerator = (org.jfree.data.category.CategoryDataset,Int,Int) ⇒ String

  /** Function alias for creating item labels for [[PieChartLike]] charts. */
  type PieSectionLabelGenerator = (org.jfree.data.general.PieDataset,Comparable[_]) ⇒ String

  /** Function alias for creating tooltips for [[PieChartLike]] charts. */
  type PieToolTipGenerator = (org.jfree.data.general.PieDataset,Comparable[_]) ⇒ String

  /** Function alias for creating item labels for [[XYChart]]s. */
  type XYItemLabelGenerator = (org.jfree.data.xy.XYDataset,Int,Int) ⇒ String

  /** Function alias for creating tooltips for [[XYChart]]s. */
  type XYToolTipGenerator = (org.jfree.data.xy.XYDataset,Int,Int) ⇒ String

  // -----------------------------------------------------------------------------------------------
  // implicit conversions between scala-chart and JFreeChart
  // -----------------------------------------------------------------------------------------------

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
