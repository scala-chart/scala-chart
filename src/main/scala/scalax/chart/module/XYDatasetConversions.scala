package scalax.chart
package module

import language.higherKinds

import scala.collection.{ GenTraversableOnce => Coll }

/** $XYDatasetConversionsInfo */
object XYDatasetConversions extends XYDatasetConversions

/** $XYDatasetConversionsInfo
  *
  * @define XYDatasetConversionsInfo Provides converters for datasets used for xy charts.
  */
trait XYDatasetConversions extends Converting with RichChartingCollections {

  abstract class ToXYDataset[A] protected () extends Converter[A] {
    type X <: XYDataset
  }

  object ToXYDataset extends ConverterCompanion[XYDataset,ToXYDataset] {
    def apply[A,B <: XYDataset](f: A => B): ToXYDataset[A] = new ToXYDataset[A] {
      type X = B
      def convert(a: A): X = f(a)
    }

    implicit val FromXYSeries: ToXYDataset[XYSeries] =
      ToIntervalXYDataset.FromXYSeries

    implicit def FromCollXYSeries[CC[X] <: Coll[X]]: ToXYDataset[CC[XYSeries]] =
      ToIntervalXYDataset.FromCollXYSeries

    implicit def FromTuple2s[A: Numeric, B: Numeric, CC[X] <: Coll[X]]: ToXYDataset[CC[(A,B)]] =
      ToIntervalXYDataset.FromTuple2s

    implicit def FromCategorizedTuple2s[A, B: Numeric, C: Numeric, CC[X] <: Coll[X], DD[X] <: Coll[X]](implicit evA: A => Comparable[A]): ToXYDataset[CC[(A,DD[(B,C)])]] =
      ToIntervalXYDataset.FromCategorizedTuple2s

    implicit val FromTimePeriodValues: ToXYDataset[TimePeriodValues] =
      ToIntervalXYDataset.FromTimePeriodValues

    implicit def FromCollTimePeriodValues[CC[X] <: Coll[X]]: ToXYDataset[CC[TimePeriodValues]] =
      ToIntervalXYDataset.FromCollTimePeriodValues

    implicit val FromTimeSeries: ToXYDataset[TimeSeries] =
      ToIntervalXYDataset.FromTimeSeries

    implicit def FromCollTimeSeries[CC[X] <: Coll[X]]: ToXYDataset[CC[TimeSeries]] =
      ToIntervalXYDataset.FromCollTimeSeries

    implicit val FromYIntervalSeries: ToXYDataset[YIntervalSeries] =
      ToIntervalXYDataset.FromYIntervalSeries

    implicit def FromCollYIntervalSeries[CC[X] <: Coll[X]]: ToXYDataset[CC[YIntervalSeries]] =
      ToIntervalXYDataset.FromCollYIntervalSeries

    implicit def FromTuple4s[A: Numeric, B: Numeric, C: Numeric, D: Numeric, CC[X] <: Coll[X]]: ToXYDataset[CC[(A,B,C,D)]] =
      ToIntervalXYDataset.FromTuple4s

    implicit def FromCategorizedTuple4s[A, B: Numeric, C: Numeric, D: Numeric, E: Numeric, CC[X] <: Coll[X], DD[X] <: Coll[X]](implicit evA: A => Comparable[A]): ToXYDataset[CC[(A,DD[(B,C,D,E)])]] =
      ToIntervalXYDataset.FromCategorizedTuple4s
  }

  abstract class ToIntervalXYDataset[A] protected () extends ToXYDataset[A] {
    type X <: IntervalXYDataset
  }

  object ToIntervalXYDataset extends ConverterCompanion[IntervalXYDataset,ToIntervalXYDataset] {
    def apply[A,B <: IntervalXYDataset](f: A => B): ToIntervalXYDataset[A] = new ToIntervalXYDataset[A] {
      type X = B
      def convert(a: A): X = f(a)
    }

    implicit val FromXYSeries: ToIntervalXYDataset[XYSeries] =
      apply(new XYSeriesCollection(_))

    implicit def FromCollXYSeries[CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[XYSeries]] =
      apply(_.foldLeft(new XYSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit def FromTuple2s[A: Numeric, B: Numeric, CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[(A,B)]] =
      apply(_.toXYSeriesCollection())

    implicit def FromCategorizedTuple2s[A, B: Numeric, C: Numeric, CC[X] <: Coll[X], DD[X] <: Coll[X]](implicit evA: A => Comparable[A]): ToIntervalXYDataset[CC[(A,DD[(B,C)])]] =
      apply(_.toXYSeriesCollection())

    implicit val FromTimePeriodValues: ToIntervalXYDataset[TimePeriodValues] =
      apply(new TimePeriodValuesCollection(_))

    implicit def FromCollTimePeriodValues[CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[TimePeriodValues]] =
      apply(_.foldLeft(new TimePeriodValuesCollection) { (dataset,series) =>
        dataset addSeries series
        dataset
      })

    implicit val FromTimeSeries: ToIntervalXYDataset[TimeSeries] =
      apply(new TimeSeriesCollection(_))

    implicit def FromCollTimeSeries[CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[TimeSeries]] =
      apply(_.foldLeft(new TimeSeriesCollection) { (dataset, series) =>
        dataset addSeries series
        dataset
      })

    implicit val FromYIntervalSeries: ToIntervalXYDataset[YIntervalSeries] =
      apply { a =>
        val dataset = new YIntervalSeriesCollection
        dataset addSeries a
        dataset
      }

    implicit def FromCollYIntervalSeries[CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[YIntervalSeries]] =
      apply(_.foldLeft(new YIntervalSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      })

    implicit def FromTuple4s[A: Numeric, B: Numeric, C: Numeric, D: Numeric, CC[X] <: Coll[X]]: ToIntervalXYDataset[CC[(A,B,C,D)]] =
      apply(_.toYIntervalSeriesCollection())

    implicit def FromCategorizedTuple4s[A, B: Numeric, C: Numeric, D: Numeric, E: Numeric, CC[X] <: Coll[X], DD[X] <: Coll[X]](implicit evA: A => Comparable[A]): ToIntervalXYDataset[CC[(A,DD[(B,C,D,E)])]] =
      apply(_.toYIntervalSeriesCollection())
  }

}
