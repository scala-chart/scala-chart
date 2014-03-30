package scalax.chart
package module

import language.higherKinds

/** $ConvertingInfo */
object Converting extends Converting

/** $ConvertingInfo
  *
  * @define ConvertingInfo Provides the [[Converter]] type class and a generic
  * [[ConverterCompanion]] for it.
  */
trait Converting {

  /** A type class that converts instances of type `A` to instances of type `X`. */
  abstract class Converter[A] protected () {

    /** The output type of this converter. */
    type X

    /** Returns a new `X` converted from the given `A`. */
    def convert(a: A): X

  }

  /** A generic companion for [[Converter]] type classes.
    *
    * @tparam XX constrains the output type `X` of the converter
    * @tparam C the converter type
    */
  abstract class ConverterCompanion[XX, C[X] <: Converter[X]] protected () {

    /** Returns a new converter based on the given conversion function. */
    def apply[A,B <: XX](f: A => B): C[A]

    /** Implicitly resolves and returns a converter. */
    @inline final def apply[A](implicit Converter: C[A]): C[A] = Converter

    /** Returns the identity converter. */
    final implicit def Identity[A <: XX]: C[A] =
      apply[A,A](identity)

  }

}
