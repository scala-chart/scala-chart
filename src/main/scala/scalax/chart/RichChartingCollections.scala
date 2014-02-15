package scalax.chart

import language.higherKinds

import java.util.Date

import scala.collection.JavaConverters.seqAsJavaListConverter
import scala.collection.GenTraversableOnce
import scala.math.Numeric.Implicits._

import org.jfree.data.category._
import org.jfree.data.general._
import org.jfree.data.statistics._
import org.jfree.data.time._
import org.jfree.data.xy._

/** $RichChartingCollectionsInfo */
object RichChartingCollections extends RichChartingCollections

/** $RichChartingCollectionsInfo
  *
  * @define RichChartingCollectionsInfo Contains enrichments for collections for conversions to
  * datasets. To see which conversions are provided have a look at the classes defined below.
  *
  * @define seriesname the name of the series (will show in the legend)
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
      * @param name $seriesname
      *
      * @usecase def toBoxAndWhiskerCategoryDataset(name: String): BoxAndWhiskerCategoryDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerCategoryDataset[C: Numeric](name: Comparable[_] = "")(implicit eva: A => Comparable[A], evc: B => Seq[C]): BoxAndWhiskerCategoryDataset = {
      trav.foldLeft(new DefaultBoxAndWhiskerCategoryDataset()) { case (dataset,(category,values)) =>
        dataset.add(calculateBoxAndWhiskerStatistics(values), name, category)
        dataset
      }
    }

    /** Converts this collection to a `BoxAndWhiskerXYDataset`.
      *
      * @param name $seriesname
      *
      * @usecase def toBoxAndWhiskerXYDataset(name: String): BoxAndWhiskerXYDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerXYDataset[C: Numeric](name: Comparable[_] = "")(implicit eva: A => Date, evb: B => Seq[C]): BoxAndWhiskerXYDataset = {
      trav.foldLeft(new DefaultBoxAndWhiskerXYDataset(name)) { case (dataset,(date,ys)) =>
        dataset.add(date, calculateBoxAndWhiskerStatistics(ys))
        dataset
      }
    }

    /** Converts this collection to a `CategoryDataset`.
      *
      * @param name $seriesname
      *
      * @usecase def toCategoryDataset(name: String): CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(name: Comparable[_] = "")(implicit eva: A => Comparable[A], numb: Numeric[B]): CategoryDataset = {
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(category,value)) =>
        dataset.addValue(value.toDouble, name, category)
        dataset
      }
    }

    /** Converts this collection to a `PieDataset`.
      *
      * @param name $seriesname
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
      * @param name $seriesname
      *
      * @usecase def toTimePeriodValues(name: String): TimePeriodValues
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
      * @param name $seriesname
      *
      * @usecase def toTimePeriodValuesCollection(name: String): TimePeriodValuesCollection
      *   @inheritdoc
      */
    def toTimePeriodValuesCollection(name: String = "")(implicit eva: A => TimePeriod, numb: Numeric[B]): TimePeriodValuesCollection =
      new TimePeriodValuesCollection(toTimePeriodValues(name))

    /** Converts this collection to a `TimeSeries`.
      *
      * @param name $seriesname
      *
      * @usecase def toTimeSeries(name: String): TimeSeries
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
      * @param name $seriesname
      *
      * @usecase def toTimeSeriesCollection(name: String): TimeSeriesCollection
      *   @inheritdoc
      */
    def toTimeSeriesCollection(name: Comparable[_] = "")(implicit eva: A => RegularTimePeriod, numb: Numeric[B]): TimeSeriesCollection =
      new TimeSeriesCollection(toTimeSeries(name))

    /** Converts this collection to an `XYSeries`.
      *
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeries(name: String): XYSeries
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
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeriesCollection(name: String): XYSeriesCollection
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
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(series,category,value)) =>
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
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(name: String): YIntervalSeries
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
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(name: String): YIntervalSeriesCollection
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
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(name: String): YIntervalSeries
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
      * @param name $seriesname
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(name: String): YIntervalSeriesCollection
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
      trav.foldLeft(new DefaultCategoryDataset) { case (dataset,(series,catvals)) =>

        catvals.foldLeft(dataset) { case (dataset,(category,value)) =>
          dataset.addValue(value.toDouble, series, category)
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
      trav.foldLeft(new TimeTableXYDataset) { case (dataset,(category,timevals)) =>

        timevals.foldLeft(dataset) { case (dataset,(time,value)) =>
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
      (implicit eva: A => Comparable[A], numb: Numeric[B], numc: Numeric[C]): IntervalXYDataset = {

      val seqop = (xs: List[XYSeries], catxys: (A,GenTraversableOnce[(B,C)])) => {
        val (category,xys) = catxys
        xys.toXYSeries(category, autoSort, allowDuplicateXValues) :: xs
      }

      DatasetConversions.ToIntervalXYDataset.CollOfXYSeriesToIntervalXYDataset toDataset {
        trav.aggregate(List[XYSeries]())(seqop, _ ::: _).reverse
      }
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
      (implicit eva: A => Comparable[_], evb: B => Double, evc: C => Double, evd: D => Double, eve: E => Double): IntervalXYDataset = {

      val seqop = (l: List[YIntervalSeries], catvals: (A,GenTraversableOnce[(B,C,D,E)])) => {
        val (name,data) = catvals
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues) :: l
      }

      DatasetConversions.ToIntervalXYDataset.CollOfYIntervalSeriesToIntervalXYDataset toDataset {
        trav.aggregate(List[YIntervalSeries]())(seqop, _ ::: _).reverse
      }
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
      (implicit eva: A => Comparable[_], evb: B => Double, evc: C => Double, evd: D => Double, eve: E => Double): IntervalXYDataset = {

      val seqop = (l: List[YIntervalSeries], catvals: (A,GenTraversableOnce[(B,(C,D,E))])) => {
        val (name,data) = catvals
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues) :: l
      }

      DatasetConversions.ToIntervalXYDataset.CollOfYIntervalSeriesToIntervalXYDataset toDataset {
        trav.aggregate(List[YIntervalSeries]())(seqop, _ ::: _).reverse
      }
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

}
