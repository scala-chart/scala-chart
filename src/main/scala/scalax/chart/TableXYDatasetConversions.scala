package scalax.chart
package module

import language.higherKinds

import scala.collection.GenTraversableOnce

import org.jfree.data.time.TimePeriod

import Imports._
import RichChartingCollections._

private[chart] trait TableXYDatasetConversions {

  abstract class ToTableXYDataset[A] protected () extends ToDataset[A] {
    type X <: TableXYDataset
  }

  object ToTableXYDataset extends ToDatasetCompanion[TableXYDataset,ToTableXYDataset] {
    def apply[A,B <: TableXYDataset](f: A => B): ToTableXYDataset[A] = new ToTableXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def CategorizedTuple2sToTableXYDataset[A <% String,B: Numeric, C: Numeric, BB[X] <: GenTraversableOnce[X], CC[X] <: GenTraversableOnce[X]]: ToTableXYDataset[CC[(A,BB[(B,C)])]] =
      apply(_.toCategoryTableXYDataset)

    implicit def CategorizedTimedValuesToTableXYDataset[A <% Comparable[A],B <% TimePeriod, C: Numeric, BB[X] <: GenTraversableOnce[X], CC[X] <: GenTraversableOnce[X]]: ToTableXYDataset[CC[(A,BB[(B,C)])]] =
      apply(_.toTimeTable)
  }

}
