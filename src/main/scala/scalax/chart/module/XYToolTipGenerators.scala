package scalax.chart
package module

import org.jfree.chart.labels.{ XYToolTipGenerator => JXYToolTipGenerator }

/** $XYToolTipGeneratorsInfo */
object XYToolTipGenerators extends XYToolTipGenerators

/** $XYToolTipGeneratorsInfo
  *
  * @define XYToolTipGeneratorsShortInfo [[XYToolTipGenerators]] contains factories for creating
  * tool tip generators for xy charts.
  *
  * {{{
  * chart.tooltipGenerator = XYToolTipGenerator(value => value.toString)
  * }}}
  *
  * @define XYToolTipGeneratorsInfo '' '' $XYToolTipGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set tool tip generators for
  * xy charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = XYLineChart(data)
  *
  * chart.tooltipGenerator = (dataset: XYDataset, series: Comparable[_], item: Int) =>
  *   val idx = dataset.indexOf(series)
  *   dataset.getY(idx, item).toString
  * }
  *
  * chart.tooltipGenerator = XYToolTipGenerator { (dataset, series, item) =>
  *   val idx = dataset.indexOf(series)
  *   dataset.getY(idx,item).toString
  * }
  *
  * chart.tooltipGenerator = XYToolTipGenerator(value => value.toString)
  *
  * chart.tooltipGenerator = XYToolTipGenerator(_.toString)
  *
  * chart.tooltipGenerator = XYToolTipGenerator((x,y) => s"""(\$x,\$y)""")
  *
  * chart.tooltipGenerator = XYToolTipGenerator.Default
  * }}}
  */
trait XYToolTipGenerators extends Imports {

  /** Tool tip generator factory for xy charts. */
  object XYToolTipGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: XYToolTipGenerator): XYToolTipGenerator = generator

    /** Returns a new tool tip generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): XYToolTipGenerator =
      XYToolTipGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        val value = dataset.getY(idx, item)
        f(value)
      }

    /** Returns a new tool tip generator by converting (x,y) pairs to strings. */
    final def apply(f: (java.lang.Number,java.lang.Number) => String): XYToolTipGenerator =
      XYToolTipGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        val x = dataset.getX(idx, item)
        val y = dataset.getY(idx, item)
        f(x,y)
      }

    /** Returns a new tool tip generator using an explicit peer. */
    final def fromPeer(jfree: JXYToolTipGenerator): XYToolTipGenerator =
      XYToolTipGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        jfree.generateToolTip(dataset, idx, item)
      }

    /** Returns a new peer. */
    final def toPeer(generator: XYToolTipGenerator): JXYToolTipGenerator = new JXYToolTipGenerator {
      override final def generateToolTip(dataset: XYDataset, seriesIndex: Int, item: Int): String = {
        val series = dataset.getSeriesKey(seriesIndex)
        generator(dataset, series, item)
      }
    }

    /** Returns a new xy tool tip generator which simply calls `toString` on values. */
    final def Default: XYToolTipGenerator =
      XYToolTipGenerator((x,y) => s"""($x,$y)""")
  }

}
