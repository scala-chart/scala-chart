package scalax.chart

import language.reflectiveCalls

import scala.collection.JavaConverters.asScalaIteratorConverter

import java.util.Collection

/** $MarkingInfo */
object Marking extends Marking

/** $MarkingInfo
  *
  * @define MarkingInfo [[Marking]] contains enrichments concerning the handling of markers in plots.
  *
  * {{{
  * import scalax.chart._
  * import scalax.chart.Charting._
  *
  * val data = Seq((0,0),(1,1),(2,2)).toXYSeriesCollection("some data")
  * val chart = XYLineChart(data)
  *
  * chart.plot.domain.markers += 1
  *
  * chart.show
  * }}}
  */
trait Marking extends Imports {

  /** Converts some type `A` to a marker. */
  trait ToMarker[A] {
    type X <: Marker
    def toMarker(a: A): X
  }

  /** Contains default [[ToMarker]] instances. */
  object ToMarker {
    implicit def MarkerToMarker[A <: Marker]: ToMarker[A] = new ToMarker[A] {
      type X = A
      def toMarker(a: A): X = a
    }

    implicit def NumericToMarker[A: Numeric]: ToMarker[A] = new ToMarker[A] {
      type X = ValueMarker
      def toMarker(a: A): X = new ValueMarker(implicitly[Numeric[A]].toDouble(a))
    }

    implicit def NumericTupleToMarker[A: Numeric, B: Numeric]: ToMarker[(A,B)] = new ToMarker[(A,B)] {
      type X = IntervalMarker
      def toMarker(t: (A,B)): X = {
        val start = implicitly[Numeric[A]].toDouble(t._1)
        val end   = implicitly[Numeric[B]].toDouble(t._2)

        new IntervalMarker(start, end)
      }
    }
  }

  /** Converts some type `A` to a category marker. */
  trait ToCategoryMarker[A] {
    type X <: CategoryMarker
    def toMarker(a: A): X
  }

  /** Contains default [[ToCategoryMarker]] instances. */
  object ToCategoryMarker {
    implicit def MarkerToCategoryMarker[A <: CategoryMarker]: ToCategoryMarker[A] = new ToCategoryMarker[A] {
      type X = A
      def toMarker(a: A): X = a
    }

    implicit def ComparableToCategoryMarker[A <% Comparable[A]]: ToCategoryMarker[A] = new ToCategoryMarker[A] {
      type X = CategoryMarker
      def toMarker(a: A): X = new CategoryMarker(a)
    }
  }

  /** Enriches a plot to handle marking on the domain and range axes. */
  implicit class MarkableCategoryPlot(underlying: CategoryPlot) {
    object domain {
      object markers {
        def +=[A: ToCategoryMarker](marker: A): this.type = {
          underlying.addDomainMarker(implicitly[ToCategoryMarker[A]].toMarker(marker))
          this
        }

        def iterator: Iterator[CategoryMarker] = {
          val jcoll = underlying.getDomainMarkers(Layer.Foreground).asInstanceOf[Collection[CategoryMarker]]
          jcoll.iterator.asScala
        }
      }
    }

    object range {
      object markers {
        def +=[A: ToMarker](marker: A): this.type = {
          underlying.addRangeMarker(implicitly[ToMarker[A]].toMarker(marker))
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = underlying.getRangeMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }
  }

  /** Enriches a plot to handle marking on the domain and range axes. */
  implicit class MarkableXYPlot(underlying: XYPlot) {
    object domain {
      object markers {
        def +=[A: ToMarker](marker: A): this.type = {
          underlying.addDomainMarker(implicitly[ToMarker[A]].toMarker(marker))
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = underlying.getDomainMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }

    object range {
      object markers {
        def +=[A: ToMarker](marker: A): this.type = {
          underlying.addRangeMarker(implicitly[ToMarker[A]].toMarker(marker))
          this
        }

        def iterator: Iterator[Marker] = {
          val jcoll = underlying.getRangeMarkers(Layer.Foreground).asInstanceOf[Collection[Marker]]
          jcoll.iterator.asScala
        }
      }
    }
  }

}
