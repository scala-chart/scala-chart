package scalax.chart
package module

import language.higherKinds

import scala.collection.{ GenTraversableOnce => Coll }

import org.jfree.data.time.TimePeriod

/** $TableXYDatasetConversionsInfo */
object TableXYDatasetConversions extends TableXYDatasetConversions

/** $TableXYDatasetConversionsInfo
  *
  * @define TableXYDatasetConversionsInfo Provides converters for `TableXYDatasets`.
  */
trait TableXYDatasetConversions extends Converting with RichChartingCollections {

  abstract class ToTableXYDataset[A] protected () extends Converter[A] {
    type X <: TableXYDataset
  }

  object ToTableXYDataset extends ConverterCompanion[TableXYDataset,ToTableXYDataset] {
    final def apply[A,B <: TableXYDataset](f: A => B): ToTableXYDataset[A] = new ToTableXYDataset[A] {
      type X = B
      override final def convert(a: A): X = f(a)
    }

    implicit def FromCategorizedTuple2s[A <% String, B: Numeric, C: Numeric, BB[X] <: Coll[X], CC[X] <: Coll[X]]: ToTableXYDataset[CC[(A,BB[(B,C)])]] =
      apply(_.toCategoryTableXYDataset)

    implicit def FromCategorizedTimeTuple2s[A <% Comparable[A], B <% TimePeriod, C: Numeric, BB[X] <: Coll[X], CC[X] <: Coll[X]]: ToTableXYDataset[CC[(A,BB[(B,C)])]] =
      apply(_.toTimeTable)
  }

}
