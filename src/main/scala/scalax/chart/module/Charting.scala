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
  * == Labels ==
  *
  * $LabelGeneratorsShortInfo
  *
  * == Rich Plots ==
  *
  * $RichPlotShortInfo
  *
  * == Tool Tips ==
  *
  * $ToolTipGeneratorsShortInfo
  */
trait Charting extends ChartFactories with DatasetConversions with Exporting
    with RichPlot with LabelGenerators with ToolTipGenerators
