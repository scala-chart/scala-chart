package scalax.chart
package build

import sbt._
import Keys._

import Publishing._
import ProjectDescription._

object ChartProject {
  def apply(name: String, path: String) = (
    Project(name, file(path))
    settings((commonSettings ++ projectDescription ++ publishing): _*)
  )
}
