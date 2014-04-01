package scalax.chart
package build

import sbt._

object Dependencies {
  val jfreechart = "org.jfree"   % "jfreechart" % "1.0.17"
  val itext      = "com.lowagie" % "itext"      % "4.2.1"

  def Specs2(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
    case Some((2,11)) => List("org.specs2" %% "specs2-core" % "2.3.10")
    case Some((2,10)) => List("org.specs2" %% "specs2-core" % "2.3.10")
    case _            => Nil
  }

  def Swing(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
    case Some((2,minor)) if minor >= 11 => "org.scala-lang.modules" %% "scala-swing" % "1.0.1"
    case _                              => "org.scala-lang"         %  "scala-swing" % scalaVersion
  }
}
