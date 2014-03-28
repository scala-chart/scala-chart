package scalax.chart
package module

/** $ImportsInfo */
object Imports extends Imports

/** $ImportsInfo
  *
  * @define ImportsInfo Contains imports from foreign packages.
  */
trait Imports {
  type JFreeChart = org.jfree.chart.JFreeChart

  type Color = java.awt.Color
  type Paint = java.awt.Paint

  type Orientation = scala.swing.Orientation.Value
  val Orientation = scala.swing.Orientation

  type BoxAndWhiskerCategoryDataset = org.jfree.data.statistics.BoxAndWhiskerCategoryDataset
  type BoxAndWhiskerXYDataset = org.jfree.data.statistics.BoxAndWhiskerXYDataset
  type CategoryDataset = org.jfree.data.category.CategoryDataset
  type IntervalXYDataset = org.jfree.data.xy.IntervalXYDataset
  type PieDataset = org.jfree.data.general.PieDataset
  type TableXYDataset = org.jfree.data.xy.TableXYDataset
  type TimePeriodValues = org.jfree.data.time.TimePeriodValues
  type TimePeriodValuesCollection = org.jfree.data.time.TimePeriodValuesCollection
  type TimeSeries = org.jfree.data.time.TimeSeries
  type TimeSeriesCollection = org.jfree.data.time.TimeSeriesCollection
  type TimeTableXYDataset = org.jfree.data.time.TimeTableXYDataset
  type XYDataset = org.jfree.data.xy.XYDataset
  type XYSeries = org.jfree.data.xy.XYSeries
  type XYSeriesCollection = org.jfree.data.xy.XYSeriesCollection
  type YIntervalSeries = org.jfree.data.xy.YIntervalSeries
  type YIntervalSeriesCollection = org.jfree.data.xy.YIntervalSeriesCollection

  type CategoryPlot = org.jfree.chart.plot.CategoryPlot
  type MultiplePiePlot = org.jfree.chart.plot.MultiplePiePlot
  type PiePlot = org.jfree.chart.plot.PiePlot
  type PiePlot3D = org.jfree.chart.plot.PiePlot3D
  type RingPlot = org.jfree.chart.plot.RingPlot
  type XYPlot = org.jfree.chart.plot.XYPlot

  type Marker = org.jfree.chart.plot.Marker
  type CategoryMarker = org.jfree.chart.plot.CategoryMarker
  type IntervalMarker = org.jfree.chart.plot.IntervalMarker
  type ValueMarker = org.jfree.chart.plot.ValueMarker

  type Layer = org.jfree.ui.Layer
  object Layer {
    val Foreground = org.jfree.ui.Layer.FOREGROUND
    val Background = org.jfree.ui.Layer.BACKGROUND
  }

  /** Function alias for creating tooltips for pie charts. */
  type PieToolTipGenerator = (PieDataset,Comparable[_]) => String

}
