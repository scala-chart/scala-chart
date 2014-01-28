package scalax.chart

/** Mixin for charts which may display labels.
  *
  * @tparam G type of label generator
  */
trait Labels[G] {

  /** Optionally returns this charts label generator. */
  def labelGenerator: Option[G]

  /** Sets this charts label generator. */
  def labelGenerator_=(generator: Option[G]): Unit

  /** Sets this charts label generator. */
  final def labelGenerator_=(generator: G) {
    labelGenerator = Some(generator)
  }

}
