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
import org.jfree.chart.{ ChartFactory ⇒ JChartFactory }
import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.plot._
import org.jfree.chart.plot.PlotOrientation._

object ChartFactory extends ChartFactory

trait ChartFactory {

  def createAreaChart(dataset: XYDataset,
                      title: String = "",
                      xAxisLabel: String = "",
                      yAxisLabel: String = "",
                      orientation: PlotOrientation = VERTICAL,
                      legend: Boolean = true,
                      tooltips: Boolean = false,
                      urls: Boolean = false) = {
    val chart = JChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimePeriodValuesCollection] ||
        dataset.isInstanceOf[TimeSeriesCollection])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

  def createStackedAreaChart(dataset: TableXYDataset,
                             title: String = "",
                             xAxisLabel: String = "",
                             yAxisLabel: String = "",
                             orientation: PlotOrientation = VERTICAL,
                             legend: Boolean = true,
                             tooltips: Boolean = false,
                             urls: Boolean = false) = {
    val chart = JChartFactory.createStackedXYAreaChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimeTableXYDataset])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

  def createLineChart(dataset: XYDataset,
                      title: String = "",
                      xAxisLabel: String = "",
                      yAxisLabel: String = "",
                      orientation: PlotOrientation = VERTICAL,
                      legend: Boolean = true,
                      tooltips: Boolean = false,
                      urls: Boolean = false) = {
    val chart = JChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)

    if (dataset.isInstanceOf[TimePeriodValuesCollection] ||
        dataset.isInstanceOf[TimeSeriesCollection])
      chart.getXYPlot.setDomainAxis(new DateAxis)

    chart
  }

  def createLabelledBarChart(dataset: CategoryDataset,
                             title: String = "",
                             xAxisLabel: String = "",
                             yAxisLabel: String = "",
                             orientation: PlotOrientation = VERTICAL,
                             legend: Boolean = true,
                             tooltips: Boolean = false,
                             urls: Boolean = false) = {
    val chart = JChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, dataset, orientation, legend, tooltips, urls)
    val renderer = chart.getPlot.asInstanceOf[CategoryPlot].getRenderer
    val labelgen = new StandardCategoryItemLabelGenerator

    renderer.setBaseItemLabelsVisible(true)
    renderer.setBaseItemLabelGenerator(labelgen)

    chart
  }

}
