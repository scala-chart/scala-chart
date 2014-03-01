package scalax.chart

import org.jfree.chart.encoders.EncoderUtil

/** Provides methods for encoding a chart.
  *
  * @define dim dimension / geometry / width x height of the output
  */
private[chart] trait EncodableChart {

  self: Chart =>

  /** Returns the chart as a byte encoded JPEG image.
    *
    * @param dim $dim
    *
    * @usecase def encodeAsJPEG(): Array[Byte]
    *   @inheritdoc
    */
  def encodeAsJPEG(dim: (Int, Int) = Chart.Default.Resolution): Array[Byte] = {
    val (width, height) = dim
    val image = peer.createBufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB, null)
    EncoderUtil.encode(image, "jpeg")
  }

  /** Returns the chart as a byte encoded PNG image.
    *
    * @param dim $dim
    *
    * @usecase def encodeAsPNG(): Array[Byte]
    *   @inheritdoc
    */
  def encodeAsPNG(dim: (Int, Int) = Chart.Default.Resolution): Array[Byte] = {
    val (width, height) = dim
    val image = peer.createBufferedImage(width, height)
    EncoderUtil.encode(image, "png")
  }

}
