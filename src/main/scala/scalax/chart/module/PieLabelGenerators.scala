package scalax.chart
package module

import org.jfree.chart.labels.{ PieSectionLabelGenerator => JPieLabelGenerator }

/** $PieLabelGeneratorsInfo */
object PieLabelGenerators extends PieLabelGenerators

/** $PieLabelGeneratorsInfo
  *
  * @define PieLabelGeneratorsShortInfo [[PieLabelGenerators]] contains factories for creating
  * label generators for pie charts.
  *
  * {{{
  * chart.labelGenerator = PieLabelGenerator(value => value.toString)
  * }}}
  *
  * @define PieLabelGeneratorsInfo '' '' $PieLabelGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set label generators for pie
  * charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = PieChart(data)
  *
  * chart.labelGenerator = (dataset: PieDataset, key: Comparable[_]) =>
  *   dataset.getValue(key).toString
  *
  * chart.labelGenerator = PieLabelGenerator { (dataset, key) =>
  *   dataset.getValue(key).toString
  * }
  *
  * chart.labelGenerator = PieLabelGenerator(_.getValue(_).toString)
  *
  * chart.labelGenerator = PieLabelGenerator(value => value.toString)
  *
  * chart.labelGenerator = PieLabelGenerator(_.toString)
  *
  * chart.labelGenerator = PieLabelGenerator.Default
  * }}}
  */
trait PieLabelGenerators extends Imports {

  /** Label generator factory for pie charts. */
  object PieLabelGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: PieLabelGenerator): PieLabelGenerator = generator

    /** Returns a new label generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): PieLabelGenerator =
      PieLabelGenerator { (dataset, key) =>
        val value = dataset.getValue(key)
        f(value)
      }

    /** Returns a new label generator using an explicit peer. */
    final def fromPeer(jfree: JPieLabelGenerator): PieLabelGenerator =
      jfree.generateSectionLabel _

    /** Returns a new peer. */
    final def toPeer(generator: PieLabelGenerator): JPieLabelGenerator = new JPieLabelGenerator {
      override final def generateAttributedSectionLabel(dataset: PieDataset, key: Comparable[_]): java.text.AttributedString = null
      override final def generateSectionLabel(dataset: PieDataset, key: Comparable[_]): String =
        generator(dataset, key)
    }

    /** Returns a new pie label generator which simply calls `toString` on values. */
    final def Default: PieLabelGenerator =
      PieLabelGenerator(_.getValue(_).toString)
  }

}
