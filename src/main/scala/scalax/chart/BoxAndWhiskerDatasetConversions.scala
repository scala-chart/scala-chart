package scalax.chart

import language.higherKinds

import java.util.Date

import scala.collection.GenTraversableOnce

import RichChartingCollections._
import Imports._

private[chart] trait BoxAndWhiskerDatasetConversions {

  abstract class ToBoxAndWhiskerCategoryDataset[A] protected () extends ToDataset[A] {
    type X <: BoxAndWhiskerCategoryDataset
  }

  object ToBoxAndWhiskerCategoryDataset extends ToDatasetCompanion[BoxAndWhiskerCategoryDataset,ToBoxAndWhiskerCategoryDataset] {
    def apply[A,B <: BoxAndWhiskerCategoryDataset](f: A => B): ToBoxAndWhiskerCategoryDataset[A] = new ToBoxAndWhiskerCategoryDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def GenTraversableOnceToBoxAndWhiskerCategoryDataset[A <% Comparable[A],B: Numeric, BB <: Seq[B], CC[X] <: GenTraversableOnce[X]]: ToBoxAndWhiskerCategoryDataset[CC[(A,BB)]] =
      apply(_.toBoxAndWhiskerCategoryDataset())
  }

  abstract class ToBoxAndWhiskerXYDataset[A] protected () extends ToDataset[A] {
    type X <: BoxAndWhiskerXYDataset
  }

  object ToBoxAndWhiskerXYDataset extends ToDatasetCompanion[BoxAndWhiskerXYDataset,ToBoxAndWhiskerXYDataset] {
    def apply[A,B <: BoxAndWhiskerXYDataset](f: A => B): ToBoxAndWhiskerXYDataset[A] = new ToBoxAndWhiskerXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def GenTraversableOnceToBoxAndWhiskerXYDataset[A <% Date,B: Numeric, BB <: Seq[B], CC[X] <: GenTraversableOnce[X]]: ToBoxAndWhiskerXYDataset[CC[(A,BB)]] =
      apply(_.toBoxAndWhiskerXYDataset())
  }

}
