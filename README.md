Scala Chart Library
===================

`scala-chart` is a Scala library for creating and working with charts. It wraps [JFreeChart][], much
like `scala-swing` does with the original `javax.swing` package. This project is released under the
same license as [JFreeChart][] to make them fully license-compatible. Checkout the [API][].

Example Usage
-------------

### [sbt][]

    libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"

### [maven][]

    <dependency>
       <groupId>com.github.wookietreiber</groupId>
       <artifactId>scala-chart_${scala.version}</artifactId>
       <version>latest.integration</version>
    </dependency>

### Imports

You can import nearly all of the `scala-chart` functionality (except the views, see below) with the
following lines:

    import scalax.chart._
    import scalax.chart.Charting._

### [Dataset Conversions](http://wookietreiber.github.io/scala-chart/latest/api/index.html#scalax.chart.RichChartingCollections)

You can use the conversions to convert from ordinary Scala collections to the datasets used by
[JFreeChart][]:

    val dataset = Seq((1,2),(2,4),(3,6),(4,8)).toXYSeriesCollection("some points")

### [Chart Factories](http://wookietreiber.github.io/scala-chart/latest/api/index.html#scalax.chart.ChartFactories)

These datasets can be used by the chart factories, which differ from the [JFreeChart][] ones in the
aspect, that they make heavy use of default arguments, so you have to type as less as possible:

    val chart = XYLineChart(dataset)

The first argument is always the dataset which is the only required argument. For better readability
of your own code, you should name the other arguments:

    val chart = XYLineChart(dataset, title = "My Chart of Some Points")

There are also some enrichments for the charts themselves to display them in a window or save them
to disk:

    chart.show
    chart.saveAsPNG(new java.io.File("/tmp/chart.png"), (1024,768))
    chart.saveAsJPEG(new java.io.File("/tmp/chart.jpg"), (1024,768))
    chart.saveAsPDF(new java.io.File("/tmp/chart.pdf"), (1024,768))

### [Implicits](http://wookietreiber.github.io/scala-chart/latest/api/index.html#scalax.chart.views.package)

There are also implicit conversions / views available, but they are not contained by the above
imports, because of ambiguity issues which may arise with implicit conversions. There are different
imports available for different kinds of datasets, e.g.:

    import scalax.chart.views.XYDatasetViews._
    val data = Seq((1,2),(2,4),(3,6),(4,8))
    val chart = XYLineChart(data, title = "My Chart of Some Points")

Import these with care, it's most likely better to use the explicit conversions as shown above.


[JFreeChart]: http://jfree.org/jfreechart/
[API]: http://wookietreiber.github.com/scala-chart/latest/api/index.html
[sbt]: http://www.scala-sbt.org/
[maven]: http://maven.apache.org/


---

[![endorse](http://api.coderwall.com/wookietreiber/endorsecount.png)](http://coderwall.com/wookietreiber)

