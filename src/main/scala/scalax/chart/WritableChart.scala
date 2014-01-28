package scalax.chart

import java.io.OutputStream

import com.lowagie.text.{ Document, Rectangle }
import com.lowagie.text.pdf.{ DefaultFontMapper, FontMapper, PdfWriter }

/** Provides methods for writing a chart to an `OutputStream`.
  *
  * @define fontMapper handles mappings between Java AWT Fonts and PDF fonts
  */
trait WritableChart extends EncodableChart {

  self: Chart[_] ⇒

  /** Writes the chart as a JPEG to an output stream.
    *
    * @param os  stream to where will be written
    * @param dim $dim
    */
  def writeAsJPEG(os: OutputStream, dim: (Int,Int)) {
    os.write(encodeAsJPEG(dim))
  }

  /** Writes the chart as a PDF to an output stream.
    *
    * @param os         stream to where will be written
    * @param dim        $dim
    * @param fontMapper $fontMapper
    */
  def writeAsPDF(os: OutputStream, dim: (Int,Int), fontMapper: FontMapper = new DefaultFontMapper) {
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

  /** Writes the chart as a PNG to an output stream.
    *
    * @param os  stream to where will be written
    * @param dim $dim
    */
  def writeAsPNG(os: OutputStream, dim: (Int,Int)) {
    os.write(encodeAsPNG(dim))
  }

}
