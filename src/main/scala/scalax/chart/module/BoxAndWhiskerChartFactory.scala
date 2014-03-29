package scalax.chart
package module

import org.jfree.chart.axis._
import org.jfree.chart.labels._
import org.jfree.chart.renderer.category._
import org.jfree.chart.renderer.xy._

import Imports._

object BoxAndWhiskerChartFactory extends BoxAndWhiskerChartFactory

trait BoxAndWhiskerChartFactory extends BoxAndWhiskerDatasetConversions with DocMacros {

  /** Factory for box and whisker charts. */
  object BoxAndWhiskerChart {

    /** Creates a new box and whisker chart.
      *
      * @param data            $data
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param legend          $legend
      * @param theme           $theme
      *
      * @usecase def apply(data: BoxAndWhiskerCategoryDataset): CategoryChart = ???
      *   @inheritdoc
      */
    def apply[A: ToBoxAndWhiskerCategoryDataset](data: A,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = true)
             (implicit theme: ChartTheme = ChartTheme.Default): CategoryChart = {

      val dataset = data.toDataset

      val domainAxis = new CategoryAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)
      rangeAxis.setAutoRangeIncludesZero(false)

      val renderer = new BoxAndWhiskerRenderer()
      renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator())

      val plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer)

      CategoryChart(plot, title, legend, theme)
    }

  }

  /** Factory for box and whisker charts. */
  object XYBoxAndWhiskerChart {

    /** Creates a new box and whisker chart.
      *
      * @param data            $data
      * @param title           $title
      * @param domainAxisLabel $domainAxisLabel
      * @param rangeAxisLabel  $rangeAxisLabel
      * @param legend          $legend
      * @param theme           $theme
      *
      * @usecase def apply(data: BoxAndWhiskerXYDataset): XYChart = ???
      *   @inheritdoc
      */
    def apply[A: ToBoxAndWhiskerXYDataset](data: A,
              title: String = "",
              domainAxisLabel: String = "",
              rangeAxisLabel: String = "",
              legend: Boolean = false)
             (implicit theme: ChartTheme = ChartTheme.Default): XYChart = {

      val dataset = data.toDataset

      val domainAxis = new DateAxis(domainAxisLabel)
      val rangeAxis = new NumberAxis(rangeAxisLabel)
      rangeAxis.setAutoRangeIncludesZero(false)

      val renderer = new XYBoxAndWhiskerRenderer(10.0)

      val plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer)

      XYChart(plot, title, legend, theme)
    }

  }

}
