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
import org.jfree.data.time._
import org.jfree.data.xy._
import org.jfree.chart._
import org.jfree.chart.ChartFactory._
import org.jfree.chart.ChartUtilities._
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.plot._
import org.jfree.chart.plot.PlotOrientation._

object RichChartingCollections extends RichChartingCollections

trait RichChartingCollections {

  implicit def toRichTuple2s[A,B](it: Iterable[(A,B)]) = new RichTuple2s(it)

  class RichTuple2s[A,B](it: Iterable[(A,B)]) {

    def toXYSeries(name: String = "")(implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeries = {
      val series = new XYSeries(name)
      it foreach {
        case (x,y) ⇒ series.add(x,y)
      }
      series
    }

    def toXYSeriesCollection(name: String = "")(implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeriesCollection =
      new XYSeriesCollection(toXYSeries(name))

    def toTimeSeries(name: String = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeries = {
      val series = new TimeSeries(name)
      it foreach {
        case (time,value) ⇒ series.add(time,value)
      }
      series
    }

    def toTimeSeriesCollection(name: String = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeriesCollection =
      new TimeSeriesCollection(toTimeSeries(name))

    def toTimePeriodValues(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValues = {
      val series = new TimePeriodValues(name)
      it foreach {
        case (t,v) ⇒ series.add(t,v)
      }
      series
    }

    def toTimePeriodValuesCollection(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValuesCollection =
      new TimePeriodValuesCollection(toTimePeriodValues(name))

    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      it foreach {
        case (category,value) ⇒ dataset.addValue(value, category, "")
      }
      dataset
    }

  }

  implicit def toRichCategorizedTuple2s[A,B,C](it: Iterable[(A,Iterable[(B,C)])]) = new RichCategorizedTuple2s(it)

  class RichCategorizedTuple2s[A,B,C](it: Iterable[(A,Iterable[(B,C)])]) {

    def toTimeTable(implicit eva: A ⇒ Comparable[A], evb: B ⇒ TimePeriod, evc: C ⇒ Number): TimeTableXYDataset = {
      val dataset = new TimeTableXYDataset
      for {
        (category,tvs) ← it
        (time,value) ← tvs
      } dataset.add(time,value,category,false)
      dataset
    }

    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Comparable[B], evc: C ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      for {
        (upper,lvs) ← it
        (lower,value) ← lvs
      } dataset.addValue(value, lower, upper)
      dataset
    }

  }

  implicit def toRichTuple4s[A,B,C,D](it: Iterable[(A,Iterable[(B,Iterable[(C,D)])])]) = new RichTuple4s(it)

  class RichTuple4s[A,B,C,D](it: Iterable[(A,Iterable[(B,Iterable[(C,D)])])]) {

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

  implicit def toRichTimeSeries(it: Iterable[TimeSeries]) = new {
    def toTimeSeriesCollection: TimeSeriesCollection = {
      val coll = new TimeSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  implicit def toRichXYSeries(it: Iterable[XYSeries]) = new {
    def toXYSeriesCollection: XYSeriesCollection = {
      val coll = new XYSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  implicit def toRichTimePeriodValues(it: Iterable[TimePeriodValues]) = new {
    def toTimePeriodValuesCollection: TimePeriodValuesCollection = {
      val coll = new TimePeriodValuesCollection
      it foreach coll.addSeries
      coll
    }
  }

}
