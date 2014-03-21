package scalax.chart
package module

import java.util.Collection

import scala.collection.JavaConverters.asScalaIteratorConverter

import org.jfree.chart._
import org.jfree.chart.plot._

/** $RichPlotInfo */
object RichPlot extends RichPlot

/** $RichPlotInfo
  *
  * @define RichPlotInfo Contains enriched plot types.
  */
trait RichPlot extends Marking {

  implicit class GenericRichPlot(val plot: Plot) {
  }

  implicit class RichCategoryPlot(val plot: CategoryPlot) {
    object domain {
      object axis {
        def label: String = Option(plot.getDomainAxis.getLabel).getOrElse("")
        def label_=(label: String): Unit =
          plot.getDomainAxis.setLabel(label)
      }

      object markers {
        def +=[A: ToCategoryMarker](a: A): this.type = {
          val marker = ToCategoryMarker[A].toMarker(a)
          plot.addDomainMarker(marker)
          this
        }

        def iterator: Iterator[CategoryMarker] = {
          val jcoll = plot.getDomainMarkers(Layer.Foreground).asInstanceOf[Collection[CategoryMarker]]
          jcoll.iterator.asScala
        }
      }
    }

    object range {
      object axis {
        def label: String = Option(plot.getRangeAxis.getLabel).getOrElse("")
        def label_=(label: String): Unit =
          plot.getRangeAxis.setLabel(label)
      }

      object markers {
        def +=[A: ToMarker](a: A): this.type = {
          val marker = ToMarker[A].toMarker(a)
          plot.addRangeMarker(marker)
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = plot.getRangeMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }
  }

  implicit class RichXYPlot(val plot: XYPlot) {
    object domain {
      object axis {
        def label: String = Option(plot.getDomainAxis.getLabel).getOrElse("")
        def label_=(label: String): Unit =
          plot.getDomainAxis.setLabel(label)
      }

      object markers {
        def +=[A: ToMarker](a: A): this.type = {
          val marker = ToMarker[A].toMarker(a)
          plot.addDomainMarker(marker)
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = plot.getDomainMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }

    object range {
      object axis {
        def label: String = Option(plot.getRangeAxis.getLabel).getOrElse("")
        def label_=(label: String): Unit =
          plot.getRangeAxis.setLabel(label)
      }

      object markers {
        def +=[A: ToMarker](a: A): this.type = {
          val marker = ToMarker[A].toMarker(a)
          plot.addRangeMarker(marker)
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = plot.getRangeMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }
  }

}
