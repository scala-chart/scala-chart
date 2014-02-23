package scalax.chart

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    version      := "0.4.0-SNAPSHOT",
    scalaVersion := "2.10.3",
    crossScalaVersions := Seq("2.10.3", "2.10.4-RC2", "2.11.0-M8"),
    initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile,
    initialCommands in Compile in console += """
      import scalax.chart.api._
    """
  )

  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"

  def ChartProject(name: String, path: String) = {
    Project(name, file(path)) settings (
      (commonSettings ++ ProjectDescription.projectDescription ++ Publishing.publishing): _*
    )
  }
}
