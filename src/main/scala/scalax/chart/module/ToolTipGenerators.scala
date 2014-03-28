package scalax.chart
package module

/** $ToolTipGeneratorsInfo */
object ToolTipGenerators extends ToolTipGenerators

/** $ToolTipGeneratorsInfo
  *
  * @define ToolTipGeneratorsShortInfo [[ToolTipGenerators]] contains means to conveniently create
  * tool tip generators for all supported chart types.
  *
  * {{{
  * val chart = PieChart(data)
  * chart.tooltipGenerator = PieToolTipGenerator(value => value.toString)
  * }}}
  *
  * @define ToolTipGeneratorsInfo '' '' $ToolTipGeneratorsShortInfo
  *
  * == Tool Tips for Pie-like Charts ==
  *
  * $PieToolTipGeneratorsShortInfo
  */
trait ToolTipGenerators extends PieToolTipGenerators
