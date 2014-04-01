package scalax.chart
package module

import org.jfree.chart.labels.{ PieToolTipGenerator => JPieToolTipGenerator }

/** $PieToolTipGeneratorsInfo */
object PieToolTipGenerators extends PieToolTipGenerators

/** $PieToolTipGeneratorsInfo
  *
  * @define PieToolTipGeneratorsShortInfo [[PieToolTipGenerators]] contains factories for creating
  * tool tip generators for pie charts.
  *
  * {{{
  * chart.tooltipGenerator = PieToolTipGenerator(value => value.toString)
  * }}}
  *
  * @define PieToolTipGeneratorsInfo '' '' $PieToolTipGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set tool tip generators for pie
  * charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = PieChart(data)
  *
  * chart.tooltipGenerator = (dataset: PieDataset, key: Comparable[_]) =>
  *   dataset.getValue(key).toString
  *
  * chart.tooltipGenerator = PieToolTipGenerator { (dataset, key) =>
  *   dataset.getValue(key).toString
  * }
  *
  * chart.tooltipGenerator = PieToolTipGenerator(_.getValue(_).toString)
  *
  * chart.tooltipGenerator = PieToolTipGenerator(value => value.toString)
  *
  * chart.tooltipGenerator = PieToolTipGenerator(_.toString)
  *
  * chart.tooltipGenerator = PieToolTipGenerator.Default
  * }}}
  */
trait PieToolTipGenerators extends Imports {

  /** Tool tip generator factory for pie charts. */
  object PieToolTipGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: PieToolTipGenerator): PieToolTipGenerator = generator

    /** Returns a new tool tip generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): PieToolTipGenerator =
      PieToolTipGenerator { (dataset, key) =>
        val value = dataset.getValue(key)
        f(value)
      }

    /** Returns a new tool tip generator using an explicit peer. */
    final def fromPeer(jfree: JPieToolTipGenerator): PieToolTipGenerator =
      jfree.generateToolTip _

    /** Returns a new peer. */
    final def toPeer(generator: PieToolTipGenerator): JPieToolTipGenerator = new JPieToolTipGenerator {
      override final def generateToolTip(dataset: PieDataset, key: Comparable[_]): String =
        generator(dataset, key)
    }

    /** Returns a new pie tool tip generator which simply calls `toString` on values. */
    final def Default: PieToolTipGenerator =
      PieToolTipGenerator(_.getValue(_).toString)
  }

}
