package scalax.chart
package module

import language.higherKinds

import scala.collection.GenTraversableOnce

import RichChartingCollections._
import Imports._

object XYDatasetConversions extends XYDatasetConversions

trait XYDatasetConversions {

  abstract class ToXYDataset[A] protected () extends ToDataset[A] {
    type X <: XYDataset
  }

  object ToXYDataset extends ToDatasetCompanion[XYDataset,ToXYDataset] {
    def apply[A,B <: XYDataset](f: A => B): ToXYDataset[A] = new ToXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit val SeriesToXYDataset: ToXYDataset[XYSeries] =
      ToIntervalXYDataset.SeriesToIntervalXYDataset

    implicit def CollSeriesToXYDataset[CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[XYSeries]] =
      ToIntervalXYDataset.CollSeriesToIntervalXYDataset

    implicit def Tuple2sToXYDataset[A: Numeric, B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[(A,B)]] =
      ToIntervalXYDataset.Tuple2sToIntervalXYDataset

    implicit def CatTuple2sToXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[(A,DD[(B,C)])]] =
      ToIntervalXYDataset.CatTuple2sToIntervalXYDataset

    implicit val TimePeriodValuesToXYDataset: ToXYDataset[TimePeriodValues] =
      ToIntervalXYDataset.TimePeriodValuesToIntervalXYDataset

    implicit def CollTimePeriodValuesToXYDataset[CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[TimePeriodValues]] =
      ToIntervalXYDataset.CollTimePeriodValuesToIntervalXYDataset

    implicit val TimeSeriesToXYDataset: ToXYDataset[TimeSeries] =
      ToIntervalXYDataset.TimeSeriesToIntervalXYDataset

    implicit def CollTimeSeriesToXYDataset[CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[TimeSeries]] =
      ToIntervalXYDataset.CollTimeSeriesToIntervalXYDataset

    implicit val YIntervalSeriesToXYDataset: ToXYDataset[YIntervalSeries] =
      ToIntervalXYDataset.YIntervalSeriesToIntervalXYDataset

    implicit def CollYIntervalSeriesToXYDataset[CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[YIntervalSeries]] =
      ToIntervalXYDataset.CollYIntervalSeriesToIntervalXYDataset

    implicit def Tuple4sToXYDataset[A: Numeric, B: Numeric, C: Numeric, D: Numeric, CC[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[(A,B,C,D)]] =
      ToIntervalXYDataset.Tuple4sToIntervalXYDataset

    implicit def CatTuple4sToXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, D: Numeric, E: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToXYDataset[CC[(A,DD[(B,C,D,E)])]] =
      ToIntervalXYDataset.CatTuple4sToIntervalXYDataset
  }

  abstract class ToIntervalXYDataset[A] protected () extends ToXYDataset[A] {
    type X <: IntervalXYDataset
  }

  object ToIntervalXYDataset extends ToDatasetCompanion[IntervalXYDataset,ToIntervalXYDataset] {
    def apply[A,B <: IntervalXYDataset](f: A => B): ToIntervalXYDataset[A] = new ToIntervalXYDataset[A] {
      type X = B
      def toDataset(a: A): X = f(a)
    }

    implicit val SeriesToIntervalXYDataset: ToIntervalXYDataset[XYSeries] =
      apply(new XYSeriesCollection(_))

    implicit def CollSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[XYSeries]] =
      apply(_.foldLeft(new XYSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit def Tuple2sToIntervalXYDataset[A: Numeric, B: Numeric, CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,B)]] =
      apply(_.toXYSeriesCollection())

    implicit def CatTuple2sToIntervalXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,DD[(B,C)])]] =
      apply(_.toXYSeriesCollection())

    implicit val TimePeriodValuesToIntervalXYDataset: ToIntervalXYDataset[TimePeriodValues] =
      apply(new TimePeriodValuesCollection(_))

    implicit def CollTimePeriodValuesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[TimePeriodValues]] =
      apply(_.foldLeft(new TimePeriodValuesCollection) { (dataset,series) =>
        dataset addSeries series
        dataset
      })

    implicit val TimeSeriesToIntervalXYDataset: ToIntervalXYDataset[TimeSeries] =
      apply(new TimeSeriesCollection(_))

    implicit def CollTimeSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[TimeSeries]] =
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

    implicit def CollYIntervalSeriesToIntervalXYDataset[CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[YIntervalSeries]] =
      apply(_.foldLeft(new YIntervalSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit def Tuple4sToIntervalXYDataset[A: Numeric, B: Numeric, C: Numeric, D: Numeric, CC[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,B,C,D)]] =
      apply(_.toYIntervalSeriesCollection())

    implicit def CatTuple4sToIntervalXYDataset[A <% Comparable[A], B: Numeric, C: Numeric, D: Numeric, E: Numeric, CC[X] <: GenTraversableOnce[X], DD[X] <: GenTraversableOnce[X]]: ToIntervalXYDataset[CC[(A,DD[(B,C,D,E)])]] =
      apply(_.toYIntervalSeriesCollection())
  }

}
