package scalax.chart

/** $ImportsInfo */
object Imports extends Imports

/** $TypeImportsInfo */
object TypeImports extends TypeImports

/** $StaticForwarderImportsInfo */
object StaticForwarderImports extends StaticForwarderImports

/** $ImportsInfo
  *
  * @define ImportsInfo Contains imports from foreign packages.
  */
trait Imports extends TypeImports with StaticForwarderImports

/** $TypeImportsInfo
  *
  * @define TypeImportsInfo Contains only the type imports from foreign packages.
  */
trait TypeImports {
  type Color = java.awt.Color
  type Paint = java.awt.Paint

  type Orientation = scala.swing.Orientation.Value

  type CategoryDataset = org.jfree.data.category.CategoryDataset
  type PieDataset = org.jfree.data.general.PieDataset
  type XYDataset = org.jfree.data.xy.XYDataset

  type CategoryPlot = org.jfree.chart.plot.CategoryPlot
  type MultiplePiePlot = org.jfree.chart.plot.MultiplePiePlot
  type PiePlot = org.jfree.chart.plot.PiePlot
  type PiePlot3D = org.jfree.chart.plot.PiePlot3D
  type RingPlot = org.jfree.chart.plot.RingPlot
  type XYPlot = org.jfree.chart.plot.XYPlot
}

/** $StaticForwarderImportsInfo
  *
  * @define StaticForwarderImportsInfo Contains only the static forwarder imports from foreign packages.
  */
trait StaticForwarderImports {
  val Orientation = scala.swing.Orientation
}
