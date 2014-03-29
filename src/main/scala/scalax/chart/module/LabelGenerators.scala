package scalax.chart
package module

/** $LabelGeneratorsInfo */
object LabelGenerators extends LabelGenerators

/** $LabelGeneratorsInfo
  *
  * @define LabelGeneratorsShortInfo [[LabelGenerators]] contains means to conveniently create label
  * generators for all supported chart types.
  *
  * {{{
  * val chart = PieChart(data)
  * chart.labelGenerator = PieLabelGenerator(value => value.toString)
  * }}}
  *
  * @define LabelGeneratorsInfo '' '' $LabelGeneratorsShortInfo
  *
  * == Labels for Category Charts ==
  *
  * $CategoryLabelGeneratorsShortInfo
  *
  * == Labels for Pie-like Charts ==
  *
  * $PieLabelGeneratorsShortInfo
  *
  * == Labels for XY Charts ==
  *
  * $XYLabelGeneratorsShortInfo
  */
trait LabelGenerators extends CategoryLabelGenerators with PieLabelGenerators with XYLabelGenerators
