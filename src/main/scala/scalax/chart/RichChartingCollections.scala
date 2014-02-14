package scalax.chart

import language.higherKinds

import java.util.Date

import scala.collection.JavaConverters.seqAsJavaListConverter
import scala.collection.GenTraversableOnce

import org.jfree.data.category._
import org.jfree.data.general._
import org.jfree.data.statistics._
import org.jfree.data.time._
import org.jfree.data.xy._

import Numeric.Implicits._

/** $RichChartingCollectionsInfo */
object RichChartingCollections extends RichChartingCollections

/** $RichChartingCollectionsInfo
  *
  * @define RichChartingCollectionsInfo Contains enrichments for collections for conversions to
  * datasets. To see which conversions are provided have a look at the classes defined below.
  *
  * @define name the name of the dataset
  *
  * @define autoSort whether or not the items in the series are sorted
  *
  * @define allowDuplicateXValues whether or not duplicate x-values are allowed
  */
trait RichChartingCollections {

  protected[chart] def calculateBoxAndWhiskerStatistics[A: Numeric](xs: Seq[A]) =
    BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(xs.asJava)

  /** Enriches a collection of data pairs. */
  implicit class RichTuple2s[A,B](trav: GenTraversableOnce[(A,B)]) {

    /** Converts this collection to a `BoxAndWhiskerCategoryDataset`.
      *
      * @usecase def toBoxAndWhiskerCategoryDataset: BoxAndWhiskerCategoryDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerCategoryDataset[C: Numeric](implicit eva: A => Comparable[A], evc: B => Seq[C]): BoxAndWhiskerCategoryDataset = {
      trav.foldLeft(new DefaultBoxAndWhiskerCategoryDataset()) { case (dataset,(category,values)) =>
        dataset.add(calculateBoxAndWhiskerStatistics(values), category, "")
        dataset
      }
    }

    /** Converts this collection to a `BoxAndWhiskerXYDataset`.
      *
      * @usecase def toBoxAndWhiskerXYDataset(): BoxAndWhiskerXYDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerXYDataset[C: Numeric](name: String = "")(implicit eva: A => Date, evb: B => Seq[C]): BoxAndWhiskerXYDataset = {
      trav.foldLeft(new DefaultBoxAndWhiskerXYDataset(name)) { case (dataset,(date,ys)) =>
        dataset.add(date, calculateBoxAndWhiskerStatistics(ys))
        dataset
      }
    }

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A => Comparable[A], numb: Numeric[B]): CategoryDataset = {
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(category,value)) =>
        dataset.addValue(value.toDouble, category, "")
        dataset
      }
    }

    /** Converts this collection to a `PieDataset`.
      *
      * @usecase def toPieDataset: PieDataset
      *   @inheritdoc
      */
    def toPieDataset(implicit eva: A => Comparable[A], numb: Numeric[B]): PieDataset = {
      trav.foldLeft(new DefaultPieDataset) { case (dataset,(category,value)) =>
        dataset.setValue(category, value.toDouble)
        dataset
      }
    }

    /** Converts this collection to `TimePeriodValues`.
      *
      * @param name $name
      *
      * @usecase def toTimePeriodValues(): TimePeriodValues
      *   @inheritdoc
      */
    def toTimePeriodValues(name: String = "")(implicit eva: A => TimePeriod, numb: Numeric[B]): TimePeriodValues = {
      trav.foldLeft(new TimePeriodValues(name)) { case (series,(time,value)) =>
        series.add(time,value.toDouble)
        series
      }
    }

    /** Converts this collection to a `TimePeriodValuesCollection`.
      *
      * @param name $name
      *
      * @usecase def toTimePeriodValuesCollection(): TimePeriodValuesCollection
      *   @inheritdoc
      */
    def toTimePeriodValuesCollection(name: String = "")(implicit eva: A => TimePeriod, numb: Numeric[B]): TimePeriodValuesCollection =
      new TimePeriodValuesCollection(toTimePeriodValues(name))

    /** Converts this collection to a `TimeSeries`.
      *
      * @param name $name
      *
      * @usecase def toTimeSeries(): TimeSeries
      *   @inheritdoc
      */
    def toTimeSeries(name: Comparable[_] = "")(implicit eva: A => RegularTimePeriod, numb: Numeric[B]): TimeSeries = {
      trav.foldLeft(new TimeSeries(name)) { case (series,(time,value)) =>
        series.add(time,value.toDouble)
        series
      }
    }

    /** Converts this collection to a `TimeSeriesCollection`.
      *
      * @param name $name
      *
      * @usecase def toTimeSeriesCollection(): TimeSeriesCollection
      *   @inheritdoc
      */
    def toTimeSeriesCollection(name: Comparable[_] = "")(implicit eva: A => RegularTimePeriod, numb: Numeric[B]): TimeSeriesCollection =
      new TimeSeriesCollection(toTimeSeries(name))

    /** Converts this collection to an `XYSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeries(): XYSeries
      *   @inheritdoc
      */
    def toXYSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit numa: Numeric[A], numb: Numeric[B]): XYSeries = {
      trav.foldLeft(new XYSeries(name, autoSort, allowDuplicateXValues)) { case (series, (x,y)) =>
        series.add(x.toDouble,y.toDouble)
        series
      }
    }

    /** Converts this collection to an `XYSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeriesCollection(): XYSeriesCollection
      *   @inheritdoc
      */
    def toXYSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: Numeric[A], numb: Numeric[B]): XYSeriesCollection =
      new XYSeriesCollection(toXYSeries(name, autoSort, allowDuplicateXValues))

  }

  /** Enriches a collection of data triples. */
  implicit class RichTuple3s[A,B,C](trav: GenTraversableOnce[(A,B,C)]) {

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A => Comparable[A], evb: B => Comparable[B], numc: Numeric[C]): CategoryDataset = {
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(category,series,value)) =>
        dataset.addValue(value.toDouble, series, category)
        dataset
      }
    }

    /** Converts this collection to a `CategoryTableXYDataset`.
      *
      * @usecase def toCategoryTableXYDataset: CategoryTableXYDataset
      *   @inheritdoc
      */
    def toCategoryTableXYDataset(implicit eva: A => String, numb: Numeric[B], numc: Numeric[C]): CategoryTableXYDataset = {
      trav.foldLeft(new CategoryTableXYDataset) { case (dataset,(category,x,y)) =>
        dataset.add(x.toDouble, y.toDouble, category, false)
        dataset
      }
    }

  }

  /** Enriches a collection of data 4-tuples. */
  implicit class RichTuple4s[A,B,C,D](trav: GenTraversableOnce[(A,B,C,D)]) {

    /** Converts this collection to a `YIntervalSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(): YIntervalSeries
      *   @inheritdoc
      */
    def toYIntervalSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Double, evb: B => Double, evc: C => Double, evd: D => Double): YIntervalSeries = {
      trav.foldLeft(new YIntervalSeries(name, autoSort, allowDuplicateXValues)) { case (series,(x,y,yLow,yHigh)) =>
        series.add(x,y,yLow,yHigh)
        series
      }
    }

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Double, evb: B => Double, evc: C => Double, evd: D => Double): YIntervalSeriesCollection = {
      val series = toYIntervalSeries(name, autoSort, allowDuplicateXValues)
      val coll = new YIntervalSeriesCollection()
      coll.addSeries(series)
      coll
    }

  }

  /** Enriches a collection of mapped 3-tuples. */
  implicit class RichMappedTuple3s[A,B,C,D](trav: GenTraversableOnce[(A,(B,C,D))]) {

    /** Converts this collection to a `YIntervalSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(): YIntervalSeries
      *   @inheritdoc
      */
    def toYIntervalSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Double, evb: B => Double, evc: C => Double, evd: D => Double): YIntervalSeries = {
      trav.foldLeft(new YIntervalSeries(name, autoSort, allowDuplicateXValues)) { case (series,(x,(y,yLow,yHigh))) =>
        series.add(x,y,yLow,yHigh)
        series
      }
    }

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Double, evb: B => Double, evc: C => Double, evd: D => Double): YIntervalSeriesCollection = {
      val series = toYIntervalSeries(name, autoSort, allowDuplicateXValues)
      val coll = new YIntervalSeriesCollection()
      coll.addSeries(series)
      coll
    }

  }

  /** Enriches a collection of categorized data pairs. */
  implicit class RichCategorizedTuple2s[A,B,C](trav: GenTraversableOnce[(A,GenTraversableOnce[(B,C)])]) {

    /** Converts this collection to a `BoxAndWhiskerCategoryDataset`.
      *
      * @usecase def toBoxAndWhiskerCategoryDataset: BoxAndWhiskerCategoryDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerCategoryDataset[D: Numeric](implicit eva: A => Comparable[A], evb: B => Comparable[B], evc: C => Seq[D]): BoxAndWhiskerCategoryDataset = {
      trav.foldLeft(new DefaultBoxAndWhiskerCategoryDataset()) { case (dataset,(upper,catvals)) =>

        catvals.foldLeft(dataset) { case (dataset,(lower,values)) =>
          dataset.add(calculateBoxAndWhiskerStatistics(values), lower, upper)
          dataset
        }

        dataset
      }
    }

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A => Comparable[A], evb: B => Comparable[B], numc: Numeric[C]): CategoryDataset = {
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(upper,lvs)) =>

        lvs.foldLeft(dataset) { case (dataset,(lower,value)) =>
          dataset.addValue(value.toDouble, lower, upper)
          dataset
        }

        dataset
      }
    }

    /** Converts this collection to a `CategoryTableXYDataset`.
      *
      * @usecase def toCategoryTableXYDataset: CategoryTableXYDataset
      *   @inheritdoc
      */
    def toCategoryTableXYDataset(implicit eva: A => String, numb: Numeric[B], numc: Numeric[C]): CategoryTableXYDataset = {
      trav.foldLeft(new CategoryTableXYDataset) { case (dataset,(category,xys)) =>

        xys.foldLeft(dataset) { case (dataset,(x,y)) =>
          dataset.add(x.toDouble, y.toDouble, category, false)
          dataset
        }

        dataset
      }
    }

    /** Converts this collection to a time table.
      *
      * @usecase def toTimeTable: TimeTableXYDataset
      *   @inheritdoc
      */
    def toTimeTable(implicit eva: A => Comparable[A], evb: B => TimePeriod, numc: Numeric[C]): TimeTableXYDataset = {
      trav.foldLeft(new TimeTableXYDataset) { case (dataset,(category,tvs)) =>

        tvs.foldLeft(dataset) { case (dataset,(time,value)) =>
          dataset.add(time,value.toDouble,category,false)
          dataset
        }

        dataset
      }
    }

    /** Converts this collection to an `XYSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeriesCollection(): XYSeriesCollection
      *   @inheritdoc
      */
    def toXYSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Comparable[A], numb: Numeric[B], numc: Numeric[C]): XYSeriesCollection = {

      val seqop = (xs: List[XYSeries], catxys: (A,GenTraversableOnce[(B,C)])) => {
        val (category,xys) = catxys
        xys.toXYSeries(category, autoSort, allowDuplicateXValues) :: xs
      }

      val seriess = trav.aggregate(List[XYSeries]())(seqop, _ ::: _).reverse

      seriess.toXYSeriesCollection
    }

  }

  /** Enriches a collection of categorized 4-tuples. */
  implicit class RichCategorizedTuple4s[A,B,C,D,E](trav: GenTraversableOnce[(A,GenTraversableOnce[(B,C,D,E)])]) {

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Comparable[_], evb: B => Double, evc: C => Double, evd: D => Double, eve: E => Double): YIntervalSeriesCollection = {

      val seqop = (l: List[YIntervalSeries], catvals: (A,GenTraversableOnce[(B,C,D,E)])) => {
        val (name,data) = catvals
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues) :: l
      }

      val seriess = trav.aggregate(List[YIntervalSeries]())(seqop, _ ::: _).reverse

      seriess.toYIntervalSeriesCollection
    }

  }

  /** Enriches a collection of categorized mapped 3-tuples. */
  implicit class RichCategorizedMappedTuple3s[A,B,C,D,E](trav: GenTraversableOnce[(A,GenTraversableOnce[(B,(C,D,E))])]) {

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A => Comparable[_], evb: B => Double, evc: C => Double, evd: D => Double, eve: E => Double): YIntervalSeriesCollection = {

      val seqop = (l: List[YIntervalSeries], catvals: (A,GenTraversableOnce[(B,(C,D,E))])) => {
        val (name,data) = catvals
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues) :: l
      }

      val seriess = trav.aggregate(List[YIntervalSeries]())(seqop, _ ::: _).reverse

      seriess.toYIntervalSeriesCollection
    }

  }

  /** Enriches a collection of categorized categorized data pairs. */
  implicit class RichCategorizedCategorizedTuple2s[A,B,C,D](trav: GenTraversableOnce[(A,GenTraversableOnce[(B,GenTraversableOnce[(C,D)])])]) {

    import org.jfree.chart.JFreeChart
    import org.jfree.chart.plot._

    /** Converts this collection to a bar chart with a `CombinedDomainCategoryPlot`.
      *
      * @usecase def toCombinedDomainBarChart: CategoryChart
      *   @inheritdoc
      */
    def toCombinedDomainBarChart(implicit eva: A => Comparable[A],
                                          evb: B => Comparable[B],
                                          evc: C => Comparable[C],
                                          numd: Numeric[D]): CategoryChart = {

      val seqop = (l: List[CategoryPlot], catmivs: (A,GenTraversableOnce[(B,GenTraversableOnce[(C,D)])])) => {
        val (outer,miv) = catmivs
        val dataset = miv.toCategoryDataset
        val chart = ChartFactories.BarChart(dataset, title = outer.toString)

        chart.plot :: l
      }

      val plots = trav.aggregate(List[CategoryPlot]())(seqop, _ ::: _).reverse

      val combinedPlot = plots.foldLeft(new CombinedDomainCategoryPlot) { (combined,single) =>
        combined add single
        combined
      }

      CategoryChart.fromPeer(new JFreeChart(combinedPlot))
    }

  }

  // -----------------------------------------------------------------------------------------------
  // convert a collection of *Series to a *Collection / *Dataset
  // -----------------------------------------------------------------------------------------------

  /** Enriches a collection of `TimeSeries`. */
  implicit class RichTimeSeriesCollection(trav: GenTraversableOnce[TimeSeries]) {
    /** Converts this collection of `TimeSeries` to a `TimeSeriesCollection`. */
    def toTimeSeriesCollection: TimeSeriesCollection = {
      trav.foldLeft(new TimeSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      }
    }
  }

  /** Enriches a collection of `XYSeries`. */
  implicit class RichXYSeriesCollection(trav: GenTraversableOnce[XYSeries]) {
    /** Converts this collection of `XYSeries` to a `XYSeriesCollection`. */
    def toXYSeriesCollection: XYSeriesCollection = {
      trav.foldLeft(new XYSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      }
    }
  }

  /** Enriches a collection of `TimePeriodValues`. */
  implicit class RichTimePeriodValuesCollection(trav: GenTraversableOnce[TimePeriodValues]) {
    /** Converts this collection of `TimePeriodValues` to a `TimePeriodValuesCollection`. */
    def toTimePeriodValuesCollection: TimePeriodValuesCollection = {
      trav.foldLeft(new TimePeriodValuesCollection) { (coll, series) =>
        coll addSeries series
        coll
      }
    }
  }

  /** Enriches a collection of `YIntervalSeries`. */
  implicit class RichYIntervalSeriesCollection(trav: GenTraversableOnce[YIntervalSeries]) {
    /** Converts this collection of `YIntervalSeries` to a `YIntervalSeriesCollection`. */
    def toYIntervalSeriesCollection: YIntervalSeriesCollection = {
      trav.foldLeft(new YIntervalSeriesCollection) { (coll, series) =>
        coll addSeries series
        coll
      }
    }
  }

}
