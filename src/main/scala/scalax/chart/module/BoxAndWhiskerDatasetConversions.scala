package scalax.chart
package module

import language.higherKinds

import java.util.Date

import scala.collection.{ GenTraversableOnce => Coll }

/** $BoxDatasetConversionsInfo */
object BoxAndWhiskerDatasetConversions extends BoxAndWhiskerDatasetConversions

/** $BoxDatasetConversionsInfo
  *
  * @define BoxDatasetConversionsInfo Provides converters for datasets used for box charts.
  */
trait BoxAndWhiskerDatasetConversions extends Converting with RichChartingCollections {

  abstract class ToBoxAndWhiskerCategoryDataset[A] protected () extends Converter[A] {
    type X <: BoxAndWhiskerCategoryDataset
  }

  object ToBoxAndWhiskerCategoryDataset extends ConverterCompanion[BoxAndWhiskerCategoryDataset,ToBoxAndWhiskerCategoryDataset] {
    def apply[A,B <: BoxAndWhiskerCategoryDataset](f: A => B): ToBoxAndWhiskerCategoryDataset[A] = new ToBoxAndWhiskerCategoryDataset[A] {
      type X = B
      def convert(a: A): X = f(a)
    }

    implicit def FromCategorizedTuple2s[A, B: Numeric, BB <: Seq[B], CC[X] <: Coll[X]](implicit evA: A => Comparable[A]): ToBoxAndWhiskerCategoryDataset[CC[(A,BB)]] =
      apply(_.toBoxAndWhiskerCategoryDataset())
  }

  abstract class ToBoxAndWhiskerXYDataset[A] protected () extends Converter[A] {
    type X <: BoxAndWhiskerXYDataset
  }

  object ToBoxAndWhiskerXYDataset extends ConverterCompanion[BoxAndWhiskerXYDataset,ToBoxAndWhiskerXYDataset] {
    def apply[A,B <: BoxAndWhiskerXYDataset](f: A => B): ToBoxAndWhiskerXYDataset[A] = new ToBoxAndWhiskerXYDataset[A] {
      type X = B
      def convert(a: A): X = f(a)
    }

    implicit def FromCategorizedTuple2s[A, B: Numeric, BB <: Seq[B], CC[X] <: Coll[X]](implicit evA: A => Date): ToBoxAndWhiskerXYDataset[CC[(A,BB)]] =
      apply(_.toBoxAndWhiskerXYDataset())
  }

}
