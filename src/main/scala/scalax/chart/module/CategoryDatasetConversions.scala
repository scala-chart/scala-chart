package scalax.chart
package module

import language.higherKinds

import scala.collection.{ GenTraversableOnce => Coll }

/** $CategoryDatasetConversionsInfo */
object CategoryDatasetConversions extends CategoryDatasetConversions

/** $CategoryDatasetConversionsInfo
  *
  * @define CategoryDatasetConversionsInfo Provides converters for datasets used for category charts
  * and multiple pie charts.
  */
trait CategoryDatasetConversions extends Converting with RichChartingCollections {

  abstract class ToCategoryDataset[A] protected () extends Converter[A] {
    type X <: CategoryDataset
  }

  object ToCategoryDataset extends ConverterCompanion[CategoryDataset,ToCategoryDataset] {
    def apply[A,B <: CategoryDataset](f: A => B): ToCategoryDataset[A] = new ToCategoryDataset[A] {
      type X = B
      def convert(a: A): X = f(a)
    }

    implicit def FromTuple2s[A <% Comparable[A],B: Numeric,CC[X] <: Coll[X]]: ToCategoryDataset[CC[(A,B)]] =
      apply(_.toCategoryDataset())

    implicit def FromCategorizedTuple2s[A <% Comparable[A],B <% Comparable[B], C: Numeric, CC[X] <: Coll[X], DD[X] <: Coll[X]]: ToCategoryDataset[CC[(A,DD[(B,C)])]] =
      apply(_.toCategoryDataset)
  }

}
