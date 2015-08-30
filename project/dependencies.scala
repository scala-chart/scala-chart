package scalax.chart
package build

import sbt._

object Dependencies {
  val jfreechart = "org.jfree"    % "jfreechart" % "1.0.19"
  val jfreesvg   = "org.jfree"    % "jfreesvg"   % "3.0"
  val itext      = "com.itextpdf" % "itextpdf"   % "5.5.6"

  def Specs2(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
    case Some((2,11)) => List("org.specs2" %% "specs2-core" % "3.6.4")
    case Some((2,10)) => List("org.specs2" %% "specs2-core" % "3.6.4")
    case _            => Nil
  }

  def Swing(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
    case Some((2,minor)) if minor >= 11 => "org.scala-lang.modules" %% "scala-swing" % "1.0.2"
    case _                              => "org.scala-lang"         %  "scala-swing" % scalaVersion
  }
}
