package scalax.chart
package exporting

import java.io._

import org.jfree.chart.encoders.EncoderUtil

import com.lowagie.text.{ Document, Rectangle }
import com.lowagie.text.pdf.{ DefaultFontMapper, FontMapper, PdfWriter }

/**
  * @define os stream to where will be written
  * @define file the output file
  */
private[exporting] trait Exporter extends Any with DocMacros {
  private[exporting] final def managed[R <: Closeable](r: R)(f: R => Unit): Unit =
    try { f(r) } finally { r.close() }
}

/** Exports charts to JPEG images. */
class JPEGExporter(val chart: Chart) extends AnyVal with Exporter {

  /** Saves the chart as a JPEG image.
    *
    * @param file $file
    *
    * @usecase def saveAsJPEG(file: String): Unit
    *   @inheritdoc
    */
  def saveAsJPEG(file: String, resolution: (Int,Int) = Chart.Default.Resolution): Unit =
    managed(new FileOutputStream(file)) { os ⇒ writeAsJPEG(os, resolution) }

  /** Writes the chart as a JPEG image to the output stream.
    *
    * @param os $os
    * @param resolution $resolution
    *
    * @usecase def writeAsJPEG(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsJPEG(os: OutputStream, resolution: (Int,Int) = Chart.Default.Resolution): Unit =
    os.write(encodeAsJPEG(resolution))

  /** Returns the chart as a byte encoded JPEG image.
    *
    * @param resolution $resolution
    *
    * @usecase def encodeAsJPEG(): Array[Byte]
    *   @inheritdoc
    */
  def encodeAsJPEG(resolution: (Int, Int) = Chart.Default.Resolution): Array[Byte] = {
    val (width, height) = resolution
    val image = chart.peer.createBufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB, null)
    EncoderUtil.encode(image, "jpeg")
  }

}

/** Exports charts to PDF documents.
  *
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
class PDFExporter(val chart: Chart) extends AnyVal with Exporter {

  /** Returns a new default font mapper. */
  final def DefaultFontMapper: FontMapper =
    new DefaultFontMapper

  /** Saves the chart as a PDF document.
    *
    * @param file       $file
    * @param resolution $resolution
    * @param fontMapper $fontMapper
    *
    * @usecase def saveAsPDF(file: String): Unit
    *   @inheritdoc
    */
  def saveAsPDF(file: String, resolution: (Int,Int) = Chart.Default.Resolution, fontMapper: FontMapper = DefaultFontMapper): Unit =
    managed(new FileOutputStream(file)) { os => writeAsPDF(os, resolution, fontMapper) }

  /** Writes the chart as a PDF document to the output stream.
    *
    * @param os $os
    * @param resolution $resolution
    * @param fontMapper $fontMapper
    *
    * @usecase def writeAsPDF(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsPDF(os: OutputStream, resolution: (Int,Int) = Chart.Default.Resolution, fontMapper: FontMapper = DefaultFontMapper): Unit = {
    val (width,height) = resolution

    val pagesize = new Rectangle(width, height)
    val document = new Document(pagesize)

    try {
      val writer = PdfWriter.getInstance(document, os)
      document.open()

      val cb = writer.getDirectContent
      val tp = cb.createTemplate(width, height)
      val g2 = tp.createGraphics(width, height, fontMapper)
      val r2D = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)

      chart.peer.draw(g2, r2D)
      g2.dispose()
      cb.addTemplate(tp, 0, 0)
    } finally {
      document.close()
    }
  }

}

/** Exports charts to PNG images. */
class PNGExporter(val chart: Chart) extends AnyVal with Exporter {

  /** Saves the chart as a PNG image.
    *
    * @param file $file
    * @param resolution $resolution
    *
    * @usecase def saveAsPNG(file: String): Unit
    *   @inheritdoc
    */
  def saveAsPNG(file: String, resolution: (Int,Int) = Chart.Default.Resolution): Unit =
    managed(new FileOutputStream(file)) { os ⇒ writeAsPNG(os, resolution) }

  /** Writes the chart as a PNG image to the output stream.
    *
    * @param os $os
    * @param resolution $resolution
    *
    * @usecase def writeAsPNG(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsPNG(os: OutputStream, resolution: (Int,Int) = Chart.Default.Resolution): Unit =
    os.write(encodeAsPNG(resolution))

  /** Returns the chart as a byte encoded PNG image.
    *
    * @param resolution $resolution
    *
    * @usecase def encodeAsPNG(): Array[Byte]
    *   @inheritdoc
    */
  def encodeAsPNG(resolution: (Int, Int) = Chart.Default.Resolution): Array[Byte] = {
    val (width, height) = resolution
    val image = chart.peer.createBufferedImage(width, height)
    EncoderUtil.encode(image, "png")
  }

}
