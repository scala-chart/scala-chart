package scalax.chart

/** Mixin for charts with a domain axis. */
trait DomainAxis {

  /** Optionally returns the domain axis label. */
  def domainAxisLabel: Option[String]

  /** Labels the domain axis. */
  def domainAxisLabel_=(label: Option[String]): Unit

  /** Labels the domain axis. */
  final def domainAxisLabel_=(label: String) {
    domainAxisLabel = Option(label)
  }

}
