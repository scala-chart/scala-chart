package scalax.chart

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    version      := "0.3.0-SNAPSHOT",
    scalaVersion := "2.10.2",
    initialCommands in Compile in console += """
      import scalax.chart._
      import scalax.chart.Charting._
    """
  )

  val docURL = "https://github.com/wookietreiber/scala-chart/tree/develop€{FILE_PATH}.scala"
}
