Scala Chart Library
===================

[![Build Status](https://travis-ci.org/wookietreiber/scala-chart.svg?branch=develop)](https://travis-ci.org/wookietreiber/scala-chart)

`scala-chart` is a Scala library for creating and working with charts. It wraps [JFreeChart][], much
like `scala-swing` does with the original `javax.swing` package. This project is released under the
same license as [JFreeChart][] to make them fully license-compatible. Checkout the [API][].

Usage
-----

Add the following to your [sbt][] build:

```scala
libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"
```

In case exporting to PDF is required, also add [iText][] to your dependencies:

```scala
libraryDependencies += "com.lowagie" % "itext" % "4.2.1"
```

### Imports

All high-level convenience can be imported with the *all you can eat* import:

```scala
import scalax.chart.api._
```

For more and more *a la carte* imports, have a look at the [module package][modules] for various
selfless traits. There is also a module containing everything the `api` import does which can be
used in applications directly:

```scala
object MyChartApp extends App with scalax.chart.module.Charting {
  val data = for (i <- 1 to 5) yield (i,i)
  val chart = XYLineChart(data)
  chart.saveAsPNG("/tmp/chart.png")
}
```

### Creating Charts

Creating charts is as simple as using one of the many chart factories, which differ from the
[JFreeChart][] ones in the aspect, that they make heavy use of default arguments, so you have to
type as less as possible:

```scala
val data = for (i <- 1 to 5) yield (i,i)
val chart = XYLineChart(data)
```

The first argument is always the dataset which is the only required argument. For better readability
of your own code, you should name the other arguments:

```scala
val chart = XYLineChart(data, title = "My Chart of Some Points")
```

There are also some enrichments for the charts themselves to display them in a window or save them
to disk:

```scala
chart.show()
```

```scala
chart.saveAsPNG("/tmp/chart.png")
chart.saveAsJPEG("/tmp/chart.jpg")
chart.saveAsPDF("/tmp/chart.pdf")
```

### Animations / Live Chart Updates

You can also do some animations, i.e. perform live updates on your datasets:

```scala
val series = Seq[(Int,Int)]() toXYSeries "f(x) = sin(x)"
val chart = XYLineChart(series)
chart.show()
for (x <- -4.0 to 4 by 0.1) {
  swing.Swing onEDT {
    series.add(x,math.sin(x))
  }
  Thread.sleep(50)
}
```

### Projects Using Scala Chart

The following list contains some projects where you can see this project in action. This list is
included here because it is sometimes more useful to see how a library is used in more than simple
examples.

- [scalameter](https://github.com/scalameter/scalameter)
- [ckit](https://github.com/wookietreiber/ckit)
- [eva4s](https://github.com/wookietreiber/eva4s)
- [pretty-accounting](https://github.com/wookietreiber/pretty-accounting)


[JFreeChart]: http://jfree.org/jfreechart/
[API]: http://wookietreiber.github.com/scala-chart/latest/api/index.html
[sbt]: http://www.scala-sbt.org/
[maven]: http://maven.apache.org/
[modules]: http://wookietreiber.github.io/scala-chart/latest/api/index.html#scalax.chart.module.package
[iText]: http://itextpdf.com/


---

[![endorse](http://api.coderwall.com/wookietreiber/endorsecount.png)](http://coderwall.com/wookietreiber)

