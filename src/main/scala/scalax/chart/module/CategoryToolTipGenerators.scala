package scalax.chart
package module

import org.jfree.chart.labels.{ CategoryToolTipGenerator => JCategoryToolTipGenerator }

/** $CategoryToolTipGeneratorsInfo */
object CategoryToolTipGenerators extends CategoryToolTipGenerators

/** $CategoryToolTipGeneratorsInfo
  *
  * @define CategoryToolTipGeneratorsShortInfo [[CategoryToolTipGenerators]] contains factories for
  * creating tool tip generators for category charts.
  *
  * {{{
  * chart.tooltipGenerator = CategoryToolTipGenerator(value => value.toString)
  * }}}
  *
  * @define CategoryToolTipGeneratorsInfo '' '' $CategoryToolTipGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set tool tip generators for
  * category charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = BarChart(data)
  *
  * chart.tooltipGenerator = (dataset: CategoryDataset, series: Comparable[_], category: Comparable[_]) =>
  *   dataset.getValue(series,category).toString
  *
  * chart.tooltipGenerator = CategoryToolTipGenerator { (dataset, series, category) =>
  *   dataset.getValue(series,category).toString
  * }
  *
  * chart.tooltipGenerator = CategoryToolTipGenerator(_.getValue(_,_).toString)
  *
  * chart.tooltipGenerator = CategoryToolTipGenerator(value => value.toString)
  *
  * chart.tooltipGenerator = CategoryToolTipGenerator(_.toString)
  *
  * chart.tooltipGenerator = CategoryToolTipGenerator.Default
  * }}}
  */
trait CategoryToolTipGenerators extends Imports {

  /** Tool tip generator factory for category charts. */
  object CategoryToolTipGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: CategoryToolTipGenerator): CategoryToolTipGenerator = generator

    /** Returns a new tool tip generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): CategoryToolTipGenerator =
      CategoryToolTipGenerator { (dataset, series, category) =>
        val value = dataset.getValue(series,category)
        f(value)
      }

    /** Returns a new tool tip generator using an explicit peer. */
    final def fromPeer(jfree: JCategoryToolTipGenerator): CategoryToolTipGenerator =
      CategoryToolTipGenerator { (dataset, series, category) =>
        val row = dataset.getRowIndex(series)
        val col = dataset.getColumnIndex(category)
        jfree.generateToolTip(dataset, row, col)
      }

    /** Returns a new peer. */
    final def toPeer(generator: CategoryToolTipGenerator): JCategoryToolTipGenerator = new JCategoryToolTipGenerator {
      override final def generateToolTip(dataset: CategoryDataset, series: Int, category: Int): String =
        generator(dataset, series, category)
    }

    /** Returns a new category tool tip generator which simply calls `toString` on values. */
    final def Default: CategoryToolTipGenerator =
      CategoryToolTipGenerator(_.getValue(_,_).toString)
  }

}
