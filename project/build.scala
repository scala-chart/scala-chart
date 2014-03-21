package scalax.chart

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    version      := "0.3.0",
    scalaVersion := "2.10.3",
    crossScalaVersions := Seq("2.11.0-RC3", "2.10.4"),
    initialCommands in Compile in console += """
      |import scalax.chart._
      |import scalax.chart.Charting._
      |""".stripMargin
  )

  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"
}
