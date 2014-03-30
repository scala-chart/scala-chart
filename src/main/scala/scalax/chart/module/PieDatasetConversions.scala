package scalax.chart
package module

import language.higherKinds

import scala.collection.{ GenTraversableOnce => Coll }

/** $PieDatasetConversionsInfo */
object PieDatasetConversions extends PieDatasetConversions

/** $PieDatasetConversionsInfo
  *
  * @define PieDatasetConversionsInfo Provides converters for datasets used for pie charts and ring
  * charts.
  */
trait PieDatasetConversions extends Converting with RichChartingCollections {

  abstract class ToPieDataset[A] protected () extends Converter[A] {
    type X <: PieDataset
  }

  object ToPieDataset extends ConverterCompanion[PieDataset,ToPieDataset] {
    final def apply[A,B <: PieDataset](f: A => B): ToPieDataset[A] = new ToPieDataset[A] {
      override final type X = B
      override final def convert(a: A): X = f(a)
    }

    implicit def FromTuple2s[A <% Comparable[A], B: Numeric, CC[X] <: Coll[X]]: ToPieDataset[CC[(A,B)]] =
      apply(_.toPieDataset)
  }

}
