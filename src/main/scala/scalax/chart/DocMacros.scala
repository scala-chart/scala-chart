package scalax.chart

/** Mixin solely for the purpose of providing common documentation macros.
  *
  * == Concrete ==
  *
  * The following list contains macros that are usually overridden by concrete representations:
  *
  *  - '''`\$chart`''' = $chart
  *  - '''`\$Chart`''' = $Chart
  *
  * == Basic ==
  *
  * The following list contains basic macros that are usually not overridden:
  *
  *  - '''`\$data`''' = $data
  *  - '''`\$domainAxisLabel`''' = $domainAxisLabel
  *  - '''`\$legend`''' = $legend
  *  - '''`\$orientation`''' = $orientation
  *  - '''`\$peer`''' = $peer
  *  - '''`\$rangeAxisLabel`''' = $rangeAxisLabel
  *  - '''`\$resolution`''' = $resolution
  *  - '''`\$theme`''' = $theme
  *  - '''`\$title`''' = $title
  *
  * @define chart chart
  * @define Chart Chart
  *
  * @define data the data the $chart will visualize
  * @define domainAxisLabel the label for the domain axis
  * @define legend whether or not the $chart will contain a legend
  * @define orientation the orientation of the $chart
  * @define peer the underlying $chart
  * @define rangeAxisLabel the label for the range axis
  * @define resolution dimension / geometry / width x height
  * @define theme the theme to apply to the $chart
  * @define title the title of the $chart
  */
trait DocMacros extends Any
