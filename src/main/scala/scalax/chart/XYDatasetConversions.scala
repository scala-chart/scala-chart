package scalax.chart

import language.higherKinds

import scala.collection.GenTraversableOnce
import scala.math.Numeric.Implicits._

import RichChartingCollections._
import Imports._

private[chart] trait XYDatasetConversions {

  abstract class ToXYDataset[A] protected () extends ToDataset[A] {
    type X <: XYDataset
  }

  object ToXYDataset extends ToDatasetCompanion[XYDataset,ToXYDataset] {
    def apply[A,B <: XYDataset](f: A => B): ToXYDataset[A] = new ToXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def GenTraversableOnceXYSeriesToXYDataset[CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[XYSeries]] =
      ToIntervalXYDataset.GenTraversableOnceXYSeriesToIntervalXYDataset

    implicit val XYSeriesToXYDataset: ToXYDataset[XYSeries] =
      ToIntervalXYDataset.XYSeriesToIntervalXYDataset

    implicit def GenTraversableOnceToXYDataset[A: Numeric, B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[(A,B)]] =
      ToIntervalXYDataset.GenTraversableOnceToIntervalXYDataset
  }

  abstract class ToIntervalXYDataset[A] protected () extends ToXYDataset[A] {
    type X <: IntervalXYDataset
  }

  object ToIntervalXYDataset extends ToDatasetCompanion[IntervalXYDataset,ToIntervalXYDataset] {
    def apply[A,B <: IntervalXYDataset](f: A => B): ToIntervalXYDataset[A] = new ToIntervalXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit def GenTraversableOnceXYSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[XYSeries]] =
      apply(_.toXYSeriesCollection)

    implicit val XYSeriesToIntervalXYDataset: ToIntervalXYDataset[XYSeries] =
      apply(new XYSeriesCollection(_))

    implicit def GenTraversableOnceToIntervalXYDataset[A: Numeric, B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,B)]] =
      apply(_.toXYSeriesCollection())
  }

}
