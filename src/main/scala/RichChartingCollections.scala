/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  Copyright © 2012 Christian Krause                                                            *
 *                                                                                               *
 *  Christian Krause <kizkizzbangbang@googlemail.com>                                            *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                                               *
 *  This file is part of 'sfreechart'.                                                           *
 *                                                                                               *
 *  This project is free software: you can redistribute it and/or modify it under the terms      *
 *  of the GNU Lesser General Public License as published by the Free Software Foundation,       *
 *  either version 3 of the License, or any later version.                                       *
 *                                                                                               *
 *  This project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;    *
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    *
 *  See the GNU Lesser General Public License for more details.                                  *
 *                                                                                               *
 *  You should have received a copy of the GNU Lesser General Public License along with this     *
 *  project. If not, see <http://www.gnu.org/licenses/>.                                         *
 *                                                                                               *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package org.sfree.chart

import org.jfree.data.category._
import org.jfree.data.general._
import org.jfree.data.time._
import org.jfree.data.xy._

/** $RichChartingCollectionsInfo */
object RichChartingCollections extends RichChartingCollections

/** $RichChartingCollectionsInfo
  *
  * @define RichChartingCollectionsInfo Contains enrichments for collections for conversions to
  * datasets. To see which conversions are provided have a look at the classes defined below.
  *
  * @define name the name of the dataset
  */
trait RichChartingCollections {

  /** Enriches a collection of data pairs. */
  implicit class RichTuple2s[A,B](it: Iterable[(A,B)]) {

    /** Converts this collection to a `CategoryDataset`. */
    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      it foreach { case (category,value) ⇒ dataset.addValue(value, category, "") }
      dataset
    }

    /** Converts this collection to a `PieDataset`. */
    def toPieDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number): PieDataset = {
      val dataset = new DefaultPieDataset
      it foreach { case (category,value) ⇒ dataset.setValue(category, value) }
      dataset
    }

    /** Converts this collection to `TimePeriodValues`.
      *
      * @param name $name
      */
    def toTimePeriodValues(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValues = {
      val series = new TimePeriodValues(name)
      it foreach { case (t,v) ⇒ series.add(t,v) }
      series
    }

    /** Converts this collection to a `TimePeriodValuesCollection`.
      *
      * @param name $name
      */
    def toTimePeriodValuesCollection(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValuesCollection =
      new TimePeriodValuesCollection(toTimePeriodValues(name))

    /** Converts this collection to a `TimeSeries`.
      *
      * @param name $name
      */
    def toTimeSeries(name: Comparable[_] = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeries = {
      val series = new TimeSeries(name)
      it foreach { case (time,value) ⇒ series.add(time,value) }
      series
    }

    /** Converts this collection to a `TimeSeriesCollection`.
      *
      * @param name $name
      */
    def toTimeSeriesCollection(name: Comparable[_] = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeriesCollection =
      new TimeSeriesCollection(toTimeSeries(name))

    /** Converts this collection to an `XYSeries`.
      *
      * @param name $name
      */
    def toXYSeries(name: Comparable[_] = "")(implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeries = {
      val series = new XYSeries(name)
      it foreach { case (x,y) ⇒ series.add(x,y) }
      series
    }

    /** Converts this collection to an `XYSeriesCollection`.
      *
      * @param name $name
      */
    def toXYSeriesCollection(name: Comparable[_] = "")(implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeriesCollection =
      new XYSeriesCollection(toXYSeries(name))

  }

  /** Enriches a collection of data `Tuple3s`. */
  implicit class RichTuple3s[A,B,C](it: Iterable[(A,Iterable[(B,C)])]) {

    /** Converts this collection to a `CategoryDataset`. */
    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Comparable[B], evc: C ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      for {
        (upper,lvs) ← it
        (lower,value) ← lvs
      } dataset.addValue(value, lower, upper)
      dataset
    }

    /** Converts this collection to a `CategoryTableXYDataset`. */
    def toCategoryTableXYDataset(implicit eva: A ⇒ String, evb: B ⇒ Number, evc: C ⇒ Number): CategoryTableXYDataset = {
      val dataset = new CategoryTableXYDataset
      for {
        (category,xys) ← it
        (x,y) ← xys
      } dataset.add(x, y, category, false)
      dataset
    }

    /** Converts this collection to a time table. */
    def toTimeTable(implicit eva: A ⇒ Comparable[A], evb: B ⇒ TimePeriod, evc: C ⇒ Number): TimeTableXYDataset = {
      val dataset = new TimeTableXYDataset
      for {
        (category,tvs) ← it
        (time,value) ← tvs
      } dataset.add(time,value,category,false)
      dataset
    }

    /** Converts this collection to an `XYSeriesCollection`. */
    def toXYSeriesCollection(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number, evc: C ⇒ Number): XYSeriesCollection = {
      val seriess = for {
        (category,xys) ← it
      } yield xys.toXYSeries(category)
      seriess.toXYSeriesCollection
    }

  }

  /** Enriches a collection of data `Tuple4s`. */
  implicit class RichTuple4s[A,B,C,D](it: Iterable[(A,Iterable[(B,Iterable[(C,D)])])]) {

    import org.jfree.chart.{ ChartFactory ⇒ _, _ }
    import org.jfree.chart.plot._

    /** Converts this collection to a bar chart with a `CombinedDomainCategoryPlot`. */
    def toCombinedDomainBarChart(implicit eva: A ⇒ Comparable[A],
                                          evb: B ⇒ Comparable[B],
                                          evc: C ⇒ Comparable[C],
                                          evd: D ⇒ Number): JFreeChart = {
      val plot = new CombinedDomainCategoryPlot

      for {
        (outer,miv) ← it
        dataset     = miv.toCategoryDataset
        chart       = ChartFactory.BarChart(dataset, outer.toString)
        subplot     = chart.getPlot.asInstanceOf[CategoryPlot]
      } plot.add(subplot)

      new JFreeChart(plot)
    }

  }

  // -----------------------------------------------------------------------------------------------
  // convert a collection of *Series to a *Collection / *Dataset
  // -----------------------------------------------------------------------------------------------

  /** Enriches a collection of `TimeSeries`. */
  implicit class RichTimeSeriesCollection(it: Iterable[TimeSeries]) {
    /** Converts this collection of `TimeSeries` to a `TimeSeriesCollection`. */
    def toTimeSeriesCollection: TimeSeriesCollection = {
      val coll = new TimeSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  /** Enriches a collection of `XYSeries`. */
  implicit class RichXYSeriesCollection(it: Iterable[XYSeries]) {
    /** Converts this collection of `XYSeries` to a `XYSeriesCollection`. */
    def toXYSeriesCollection: XYSeriesCollection = {
      val coll = new XYSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  /** Enriches a collection of `TimePeriodValues`. */
  implicit class RichTimePeriodValuesCollection(it: Iterable[TimePeriodValues]) {
    /** Converts this collection of `TimePeriodValues` to a `TimePeriodValuesCollection`. */
    def toTimePeriodValuesCollection: TimePeriodValuesCollection = {
      val coll = new TimePeriodValuesCollection
      it foreach coll.addSeries
      coll
    }
  }

}
