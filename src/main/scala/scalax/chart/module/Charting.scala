package scalax.chart
package module

/** $ChartingInfo */
object Charting extends Charting

/** $ChartingInfo
  *
  * @define ChartingInfo Contains all enrichments.
  *
  * == Creating Charts ==
  *
  * $ChartFactoriesShortInfo
  *
  * == Exporting Charts ==
  *
  * $ExportingShortInfo
  *
  * == Rich Plots ==
  *
  * $RichPlotShortInfo
  *
  * == Tool Tips ==
  *
  * $ToolTipGeneratorsShortInfo
  */
trait Charting extends RichChartingCollections with ChartFactories with RichChart with Imports
    with Exporting with RichPlot with LabelGenerators with ToolTipGenerators
