package scalax.chart

import language.higherKinds

import scala.collection.GenTraversableOnce
import scala.math.Numeric.Implicits._

import org.jfree.data.category.DefaultCategoryDataset

import Imports._
import RichChartingCollections._

private[chart] trait CategoryDatasetConversions {

  abstract class ToCategoryDataset[A] protected () extends ToDataset[A] {
    type X <: CategoryDataset
  }

  object ToCategoryDataset extends ToDatasetCompanion[CategoryDataset,ToCategoryDataset] {
    def apply[A,B <: CategoryDataset](f: A => B): ToCategoryDataset[A] = new ToCategoryDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def Tuple2sToCategoryDataset[A <% Comparable[A],B: Numeric,CC[X] <: GenTraversableOnce[X]]: ToCategoryDataset[CC[(A,B)]] =
      apply(_.toCategoryDataset())

    implicit def CatTuple2sToCategoryDataset[A <% Comparable[A],B <% Comparable[B], C: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToCategoryDataset[CC[(A,DD[(B,C)])]] =
      apply(_.toCategoryDataset)
  }

}
