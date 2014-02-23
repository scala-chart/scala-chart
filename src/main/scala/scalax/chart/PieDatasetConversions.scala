package scalax.chart
package module

import language.higherKinds

import scala.collection.GenTraversableOnce
import scala.math.Numeric.Implicits._

import org.jfree.data.general.DefaultPieDataset

import Imports._
import RichChartingCollections._

private[chart] trait PieDatasetConversions {

  abstract class ToPieDataset[A] protected () extends ToDataset[A] {
    type X <: PieDataset
  }

  object ToPieDataset extends ToDatasetCompanion[PieDataset,ToPieDataset] {
    def apply[A,B <: PieDataset](f: A => B): ToPieDataset[A] = new ToPieDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def GenTraversableOnceToPieDataset[A <% Comparable[A], B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToPieDataset[CC[(A,B)]] =
      apply(_.toPieDataset)
  }

}
