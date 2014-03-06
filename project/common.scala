package scalax.chart

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    version      := "0.4.0-SNAPSHOT",
    scalaVersion := "2.11.0-RC1",
    crossScalaVersions := Seq("2.11.0-RC1", "2.10.3")
  )

  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"

  def ChartProject(name: String, path: String) = {
    Project(name, file(path)) settings (
      (commonSettings ++ ProjectDescription.projectDescription ++ Publishing.publishing): _*
    )
  }
}
