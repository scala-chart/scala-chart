package scalax.chart

/** Mixin for charts with a range axis. */
trait RangeAxis {

  /** Optionally returns the range axis label. */
  def rangeAxisLabel: Option[String]

  /** Labels the range axis. */
  def rangeAxisLabel_=(label: Option[String]): Unit

  /** Labels the range axis. */
  final def rangeAxisLabel_=(label: String) {
    rangeAxisLabel = Option(label)
  }

}
