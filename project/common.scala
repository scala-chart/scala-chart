package scalax.chart

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    scalaVersion := "2.11.0-RC4",
    crossScalaVersions := Seq("2.11.0-RC4", "2.10.4")
  )

  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"

  def ChartProject(name: String, path: String) = {
    Project(name, file(path)) settings (
      (commonSettings ++ ProjectDescription.projectDescription ++ Publishing.publishing): _*
    )
  }
}
