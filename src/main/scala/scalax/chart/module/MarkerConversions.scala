package scalax.chart
package module

/** $MarkerConversionsInfo */
object MarkerConversions extends MarkerConversions

/** $MarkerConversionsInfo
  *
  * @define MarkerConversionsShortInfo [[MarkerConversions]] contains type class based conversions
  * from certain types to `Marker` instances.
  *
  * {{{
  * chart.plot.domain.markers += 1
  * }}}
  *
  * @define MarkerConversionsInfo '' '' $MarkerConversionsShortInfo
  *
  * All numeric types and numeric ranges are supported to create simple value markers:
  *
  * {{{
  * chart.plot.range.markers += 3
  * chart.plot.range.markers += 3L
  * chart.plot.range.markers += 3.0
  * chart.plot.range.markers += ((1.0,2.0))
  * }}}
  *
  * There is also an additional conversion type class for the domain of [[CategoryChart]]s you can
  * use for `CategoryMarker`s, which support arbitrary `Comparable`s like `String`:
  *
  * {{{
  * chart.plot.domain.markers += "category"
  * }}}
  *
  * The legacy JFreeChart classes can still be used directly:
  *
  * {{{
  * chart.plot.range.markers += new ValueMarker(3.0)
  * chart.plot.range.markers += new IntervalMarker(1.0, 2.0)
  *
  * chart.plot.domain.markers += new CategoryMarker("category")
  * }}}
  */
trait MarkerConversions extends Imports {

  /** Converts some type `A` to a marker. */
  trait ToMarker[A] {
    type X <: Marker
    def toMarker(a: A): X
  }

  /** Contains default [[ToMarker]] instances. */
  object ToMarker {
    @inline final def apply[A](implicit TM: ToMarker[A]): ToMarker[A] = TM

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
    @inline final def apply[A](implicit TM: ToCategoryMarker[A]): ToCategoryMarker[A] = TM

    implicit def MarkerToCategoryMarker[A <: CategoryMarker]: ToCategoryMarker[A] = new ToCategoryMarker[A] {
      type X = A
      def toMarker(a: A): X = a
    }

    implicit def ComparableToCategoryMarker[A](implicit evA: A => Comparable[A]): ToCategoryMarker[A] = new ToCategoryMarker[A] {
      type X = CategoryMarker
      def toMarker(a: A): X = new CategoryMarker(a)
    }
  }

}
