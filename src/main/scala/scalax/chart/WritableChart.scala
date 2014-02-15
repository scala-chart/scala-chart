package scalax.chart

import java.io.OutputStream

import com.lowagie.text.{ Document, Rectangle }
import com.lowagie.text.pdf.{ FontMapper, PdfWriter }

/** Provides methods for writing a chart to an `OutputStream`.
  *
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
private[chart] trait WritableChart extends EncodableChart {

  self: Chart[_] ⇒

  /** Writes the chart as a JPEG image to the output stream.
    *
    * @param os  stream to where will be written
    * @param dim $dim
    *
    * @usecase def writeAsJPEG(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsJPEG(os: OutputStream, dim: (Int,Int) = Chart.Default.Resolution): Unit =
    os.write(encodeAsJPEG(dim))

  /** Writes the chart as a PDF document to the output stream.
    *
    * @param os         stream to where will be written
    * @param dim        $dim
    * @param fontMapper $fontMapper
    *
    * @usecase def writeAsPDF(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsPDF(os: OutputStream, dim: (Int,Int) = Chart.Default.Resolution, fontMapper: FontMapper = Chart.Default.FontMapper): Unit = {
    val (width,height) = dim

    val pagesize = new Rectangle(width, height)
    val document = new Document(pagesize)

    try {
      val writer = PdfWriter.getInstance(document, os)
      document.open()

      val cb = writer.getDirectContent
      val tp = cb.createTemplate(width, height)
      val g2 = tp.createGraphics(width, height, fontMapper)
      val r2D = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)

      peer.draw(g2, r2D)
      g2.dispose()
      cb.addTemplate(tp, 0, 0)
    } finally {
      document.close()
    }
  }

  /** Writes the chart as a PNG image to the output stream.
    *
    * @param os  stream to where will be written
    * @param dim $dim
    *
    * @usecase def writeAsPNG(os: OutputStream): Unit
    *   @inheritdoc
    */
  def writeAsPNG(os: OutputStream, dim: (Int,Int) = Chart.Default.Resolution): Unit =
    os.write(encodeAsPNG(dim))

}
