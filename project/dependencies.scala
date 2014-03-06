package scalax.chart
package build

import sbt._

object Dependencies {
  val jfreechart = "org.jfree"   % "jfreechart" % "1.0.17"
  val itext      = "com.lowagie" % "itext"      % "4.2.1"

  def specs(scalaVersion: String) = scalaVersion match {
    case v if v startsWith "2.10" => Seq("org.specs2"  %% "specs2-core" % "2.3.8" % "test")
    case v if v startsWith "2.11" => Seq("org.specs2"  %% "specs2-core" % "2.3.10" % "test")
    case _                        => Nil
  }

  def swing(scalaVersion: String) = scalaVersion match {
    case v if v startsWith "2.11" => "org.scala-lang.modules" %% "scala-swing" % "1.0.0"
    case v                        => "org.scala-lang"         %  "scala-swing" % v
  }
}
