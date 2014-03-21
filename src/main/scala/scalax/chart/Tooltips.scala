package scalax.chart

/** Mixin for charts which may display tooltips.
  *
  * @tparam G type of tooltip generator
  */
private[chart] trait Tooltips[G] {

  /** Optionally returns the tooltip generator. */
  def tooltipGenerator: Option[G]

  /** Optionally sets the tooltip generator. */
  def tooltipGenerator_=(generator: Option[G]): Unit

  /** Sets the tooltip generator. */
  final def tooltipGenerator_=(generator: G): Unit = {
    tooltipGenerator = Option(generator)
  }

}
