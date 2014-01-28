package scalax.chart

import org.jfree.chart.encoders.EncoderUtil

/** Provides methods for encoding a chart.
  *
  * @define dim dimension / geometry / width x height of the output
  */
trait EncodableChart {

  self: Chart[_] ⇒

  /** Returns the chart as a byte encoded JPEG image.
    *
    * @param dim $dim
    */
  def encodeAsJPEG(dim: (Int, Int)): Array[Byte] = {
    val (width, height) = dim
    val image = peer.createBufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB, null)
    EncoderUtil.encode(image, "jpeg")
  }

  /** Returns the chart as a byte encoded PNG image.
    *
    * @param dim $dim
    */
  def encodeAsPNG(dim: (Int, Int)): Array[Byte] = {
    val (width, height) = dim
    val image = peer.createBufferedImage(width, height)
    EncoderUtil.encode(image, "png")
  }

}
