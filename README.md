Scala Wrappers for JFreeChart
=============================

SFreeChart is a Scala-friendly wrapper for [JFreeChart][]. This project is released under the same
license as [JFreeChart][] to make the projects fully license-compatible.

Usage
-----

### [sbt][]

    libraryDependencies += "com.github.wookietreiber.sfreechart" % "sfreechart" % "latest.integration" cross CrossVersion.full

### [maven][]

    <dependency>
       <groupId>com.github.wookietreiber.sfreechart</groupId>
       <artifactId>sfreechart_${scala.version}</artifactId>
       <version>latest.integration</version>
    </dependency>

Examples
--------

You can import nearly all of SFreeChart functionality (except the views, see below) with the
following line:

    import org.sfree.chart.Charting._

You can use the conversions to convert from ordinary Scala collections to the datasets used by
[JFreeChart][]:

    val dataset = Seq((1,2),(2,4),(3,6),(4,8)).toXYSeriesCollection("some points")

These datasets can be used by the chart factory methods, which differ from the [JFreeChart][] ones
in the aspect, that they make heavy use of default arguments, so you have to type as less as
possible:

    val chart = XYLineChart(dataset, title = "My Chart of Some Points")

There are also some enrichments for the charts themselves to display them in a window or save them
to disk:

    chart.show
    chart.saveAsPNG(new java.io.File("/tmp/chart.png"), (1024,768))
    chart.saveAsJPEG(new java.io.File("/tmp/chart.jpg"), (1024,768))
    chart.saveAsPDF(new java.io.File("/tmp/chart.pdf"), (1024,768))

There are also implicit conversions / views available, but they are disabled by default, because of
ambiguity issues with implicit conversions. There are different imports available for different
kinds of views, e.g.:

    import org.sfree.chart.views.XYDatasetViews._
    val data = Seq((1,2),(2,4),(3,6),(4,8))
    val chart = XYLineChart(data, title = "My Chart of Some Points")

Import these with care, it's most likely better to use the explicit conversions as shown above.


[JFreeChart]: http://jfree.org/jfreechart/
[sbt]: http://www.scala-sbt.org/
[maven]: http://maven.apache.org/


---

[![endorse](http://api.coderwall.com/wookietreiber/endorsecount.png)](http://coderwall.com/wookietreiber)

