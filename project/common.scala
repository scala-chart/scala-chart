package scalax.chart

import sbt._
import Keys._

package object build {
  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"

  def ChartProject(name: String, path: String) = {
    Project(name, file(path)) settings (
      (ProjectDescription.projectDescription ++ Publishing.publishing)
    )
  }
}
