package scalax.chart
package module

/** $DatasetConversionsInfo */
object DatasetConversions extends DatasetConversions

/** $DatasetConversionsInfo
  *
  * @define DatasetConversionsInfo Provides all dataset converters.
  */
trait DatasetConversions extends BoxAndWhiskerDatasetConversions with CategoryDatasetConversions
    with PieDatasetConversions with TableXYDatasetConversions with XYDatasetConversions
