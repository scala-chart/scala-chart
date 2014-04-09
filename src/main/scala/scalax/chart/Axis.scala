package scalax.chart

import module.Imports._

/** Represents a line of reference.
  *
  * @define axis axis
  * @define Axis Axis
  */
abstract class Axis protected () {

  /** Returns the peer axis type. */
  type Peer <: org.jfree.chart.axis.Axis

  /** Returns the underlying $axis. */
  def peer: Peer

  /** Represents the label of an $axis.
    *
    * @param peer Returns the underlying $axis.
    */
  class Label private[Axis] () {

    /** Returns the text of this label. */
    final def text: String =
      Option(peer.getLabel).getOrElse("")

    /** Sets the text of this label. */
    final def text_=(s: String): Unit =
      peer.setLabel(s)

    /** Returns the angle of this label in radians. */
    final def angle: Double =
      peer.getLabelAngle

    /** Sets the angle of this label in radians. */
    final def angle_=(a: Double): Unit =
      peer.setLabelAngle(a)

    /** Returns the font of this label. */
    final def font: Font =
      peer.getLabelFont

    /** Sets the font of this label. */
    final def font_=(font: Font): Unit =
      peer.setLabelFont(font)

    /** Returns the insets (surrounding blank space) of this label. */
    final def insets: RectangleInsets =
      peer.getLabelInsets

    /** Sets the insets (surrounding blank space) of this label. */
    final def insets_=(insets: RectangleInsets): Unit =
      peer.setLabelInsets(insets)

    /** Returns the location of this label. */
    final def location: AxisLabelLocation =
      peer.getLabelLocation

    /** Sets the location of this label. */
    final def location_=(location: AxisLabelLocation): Unit =
      peer.setLabelLocation(location)

    /** Returns the paint used to draw this label. */
    final def paint: Paint =
      peer.getLabelPaint

    /** Sets the paint used to draw this label. */
    final def paint_=(paint: Paint): Unit =
      peer.setLabelPaint(paint)

  }

  /** Returns the label of this $axis. */
  final val label: Label = new Label()

}
