package scalax.chart
package build

import sbt._

object Dependencies {
  val jfreechart = "org.jfree"   % "jfreechart" % "1.0.17"
  val itext      = "com.lowagie" % "itext"      % "4.2.1"

  def Specs2(scalaVersion: String) = scalaVersion match {
    case v if v startsWith "2.10" => List("org.specs2" %% "specs2-core" % "2.3.10" % "test")
    case v if v startsWith "2.11" => List("org.specs2" %% "specs2-core" % "2.3.10" % "test")
    case _                        => Nil
  }

  def Swing(scalaVersion: String) = scalaVersion match {
    case v if v startsWith "2.11" => "org.scala-lang.modules" %% "scala-swing" % "1.0.1"
    case v                        => "org.scala-lang"         %  "scala-swing" % v
  }
}
