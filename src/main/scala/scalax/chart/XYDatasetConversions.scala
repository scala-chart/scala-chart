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
      ToIntervalXYDataset.CollOfXYSeriesToIntervalXYDataset

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

    implicit val XYSeriesToIntervalXYDataset: ToIntervalXYDataset[XYSeries] =
      apply(new XYSeriesCollection(_))

    implicit def GenTraversableOnceToIntervalXYDataset[A: Numeric, B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,B)]] =
      apply(_.toXYSeriesCollection())

    implicit def CatCollToIntervalXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,DD[(B,C)])]] =
      apply(_.toXYSeriesCollection())

    implicit def CollOfXYSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[XYSeries]] =
      apply(_.foldLeft(new XYSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit val TimePeriodValuesToIntervalXYDataset: ToIntervalXYDataset[TimePeriodValues] =
      apply(new TimePeriodValuesCollection(_))

    implicit def CollOfTimePeriodValuesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[TimePeriodValues]] =
      apply(_.foldLeft(new TimePeriodValuesCollection) { (dataset,series) =>
        dataset addSeries series
        dataset
      })

    implicit val TimeSeriesToIntervalXYDataset: ToIntervalXYDataset[TimeSeries] =
      apply(new TimeSeriesCollection(_))

    implicit def CollOfTimeSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[TimeSeries]] =
      apply(_.foldLeft(new TimeSeriesCollection) { (dataset, series) =>
        dataset addSeries series
        dataset
      })

    implicit val YIntervalSeriesToIntervalXYDataset: ToIntervalXYDataset[YIntervalSeries] =
      apply { a =>
        val dataset = new YIntervalSeriesCollection
        dataset addSeries a
        dataset
      }

    implicit def CollOfYIntervalSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[YIntervalSeries]] =
      apply(_.foldLeft(new YIntervalSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit def Tuple4sToIntervalXYDataset[A: Numeric, B: Numeric, C: Numeric, D: Numeric, CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,B,C,D)]] =
      apply(_.toYIntervalSeriesCollection())

    implicit def CategorizedTuple4sToIntervalXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, D: Numeric, E: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,DD[(B,C,D,E)])]] =
      apply(_.toYIntervalSeriesCollection())
  }

}
