package scalax.chart
package module

import org.jfree.chart.labels.{ XYItemLabelGenerator => JXYLabelGenerator }

/** $XYLabelGeneratorsInfo */
object XYLabelGenerators extends XYLabelGenerators

/** $XYLabelGeneratorsInfo
  *
  * @define XYLabelGeneratorsShortInfo [[XYLabelGenerators]] contains factories for creating label
  * generators for xy charts.
  *
  * {{{
  * chart.labelGenerator = XYLabelGenerator(value => value.toString)
  * }}}
  *
  * @define XYLabelGeneratorsInfo '' '' $XYLabelGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set label generators for xy
  * charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = XYLineChart(data)
  *
  * chart.labelGenerator = (dataset: XYDataset, series: Comparable[_], item: Int) =>
  *   val idx = dataset.indexOf(series)
  *   dataset.getY(idx, item).toString
  * }
  *
  * chart.labelGenerator = XYLabelGenerator { (dataset, series, item) =>
  *   val idx = dataset.indexOf(series)
  *   dataset.getY(idx,item).toString
  * }
  *
  * chart.labelGenerator = XYLabelGenerator(value => value.toString)
  *
  * chart.labelGenerator = XYLabelGenerator(_.toString)
  *
  * chart.labelGenerator = XYLabelGenerator((x,y) => s"""(\$x,\$y)""")
  *
  * chart.labelGenerator = XYLabelGenerator.Default
  * }}}
  */
trait XYLabelGenerators extends Imports {

  /** Label generator factory for xy charts. */
  object XYLabelGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: XYLabelGenerator): XYLabelGenerator = generator

    /** Returns a new label generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): XYLabelGenerator =
      XYLabelGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        val value = dataset.getY(idx, item)
        f(value)
      }

    /** Returns a new label generator by converting (x,y) pairs to strings. */
    final def apply(f: (java.lang.Number,java.lang.Number) => String): XYLabelGenerator =
      XYLabelGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        val x = dataset.getX(idx, item)
        val y = dataset.getY(idx, item)
        f(x,y)
      }

    /** Returns a new label generator using an explicit peer. */
    final def fromPeer(jfree: JXYLabelGenerator): XYLabelGenerator =
      XYLabelGenerator { (dataset, series, item) =>
        val idx = dataset.indexOf(series)
        jfree.generateLabel(dataset, idx, item)
      }

    /** Returns a new peer. */
    final def toPeer(generator: XYLabelGenerator): JXYLabelGenerator = new JXYLabelGenerator {
      override final def generateLabel(dataset: XYDataset, seriesIndex: Int, item: Int): String = {
        val series = dataset.getSeriesKey(seriesIndex)
        generator(dataset, series, item)
      }
    }

    /** Returns a new xy label generator which simply calls `toString` on values. */
    final def Default: XYLabelGenerator =
      XYLabelGenerator((x,y) => s"""($x,$y)""")
  }

}
