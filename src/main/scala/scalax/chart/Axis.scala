package scalax.chart

/** Represents a line of reference.
  *
  * @define axis axis
  * @define Axis Axis
  */
abstract class Axis protected () {

  /** Returns the underlying $axis. */
  def peer: org.jfree.chart.axis.Axis

  /** Optionally returns the label of this $axis. */
  final def label: Option[String] =
    Option(peer.getLabel).filterNot(_ == "")

  /** Labels this $axis. */
  final def label_=(label: Option[String]): Unit =
    peer.setLabel(label.getOrElse(""))

  /** Labels this $axis. */
  final def label_=(label: String): Unit =
    label_=(Option(label))

}
