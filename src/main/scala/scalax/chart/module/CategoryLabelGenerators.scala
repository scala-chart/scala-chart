package scalax.chart
package module

import org.jfree.chart.labels.{ CategoryItemLabelGenerator => JCategoryLabelGenerator }

/** $CategoryLabelGeneratorsInfo */
object CategoryLabelGenerators extends CategoryLabelGenerators

/** $CategoryLabelGeneratorsInfo
  *
  * @define CategoryLabelGeneratorsShortInfo [[CategoryLabelGenerators]] contains factories for
  * creating label generators for category charts.
  *
  * {{{
  * chart.labelGenerator = CategoryLabelGenerator(value => value.toString)
  * }}}
  *
  * @define CategoryLabelGeneratorsInfo '' '' $CategoryLabelGeneratorsShortInfo
  *
  * The following list contains various ways on how to create and set label generators for
  * category charts:
  *
  * {{{
  * val data = for (i <- 1 to 5) yield (i,i)
  * val chart = BarChart(data)
  *
  * chart.labelGenerator = (dataset: CategoryDataset, series: Comparable[_], category: Comparable[_]) =>
  *   dataset.getValue(series,category).toString
  *
  * chart.labelGenerator = CategoryLabelGenerator { (dataset, series, category) =>
  *   dataset.getValue(series,category).toString
  * }
  *
  * chart.labelGenerator = CategoryLabelGenerator(_.getValue(_,_).toString)
  *
  * chart.labelGenerator = CategoryLabelGenerator(value => value.toString)
  *
  * chart.labelGenerator = CategoryLabelGenerator(_.toString)
  *
  * chart.labelGenerator = CategoryLabelGenerator.Default
  * }}}
  */
trait CategoryLabelGenerators extends Imports {

  /** Label generator factory for category charts. */
  object CategoryLabelGenerator {

    /** Returns the given generator. This method exists solely for the purpose to avoid typing. */
    final def apply(generator: CategoryLabelGenerator): CategoryLabelGenerator = generator

    /** Returns a new label generator by converting values to strings. */
    final def apply(f: java.lang.Number => String): CategoryLabelGenerator =
      CategoryLabelGenerator { (dataset, series, category) =>
        val value = dataset.getValue(series,category)
        f(value)
      }

    /** Returns a new label generator using an explicit peer. */
    final def fromPeer(jfree: JCategoryLabelGenerator): CategoryLabelGenerator =
      CategoryLabelGenerator { (dataset, series, category) =>
        val row = dataset.getRowIndex(series)
        val col = dataset.getColumnIndex(category)
        jfree.generateLabel(dataset, row, col)
      }

    /** Returns a new peer. */
    final def toPeer(generator: CategoryLabelGenerator): JCategoryLabelGenerator = new JCategoryLabelGenerator {
      override final def generateColumnLabel(dataset: CategoryDataset, category: Int): String = ""
      override final def generateLabel(dataset: CategoryDataset, row: Int, col: Int): String = {
        val series = dataset.getRowKey(row)
        val category = dataset.getColumnKey(col)
        generator(dataset, series, category)
      }
      override final def generateRowLabel(dataset: CategoryDataset, series: Int): String = ""
    }

    /** Returns a new category label generator which simply calls `toString` on values. */
    final def Default: CategoryLabelGenerator =
      CategoryLabelGenerator(_.getValue(_,_).toString)
  }

}
