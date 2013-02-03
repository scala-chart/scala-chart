
name := "scala-chart"

version := "0.2.0"

scalaVersion := "2.10.0"

libraryDependencies <+= scalaVersion { sv ⇒
  "org.scala-lang" % "scala-swing" % sv
}

libraryDependencies ++= Seq (
  "org.jfree"  %  "jfreechart" % "1.0.14",
  "org.specs2" %% "specs2"     % "1.13" % "test"
)

initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile

initialCommands in Compile in console += """
  import scalax.chart._
  import scalax.chart.Charting._
"""

// -------------------------------------------------------------------------------------------------
// supplementary project information
// -------------------------------------------------------------------------------------------------

organization := "com.github.wookietreiber"

description := "Scala Chart Library"

homepage := Some(url("https://github.com/wookietreiber/scala-chart"))

startYear := Some(2012)

licenses := Seq (
  "GNU Lesser General Public Licence" → url("http://www.gnu.org/licenses/lgpl.txt")
)

scmInfo := Some(ScmInfo(
  url("https://github.com/wookietreiber/scala-chart"),
  "scm:git:git://github.com/wookietreiber/scala-chart.git",
  Some("scm:git:https://github.com/wookietreiber/scala-chart.git")
))

pomExtra := (
  <developers>
    <developer>
      <id>wookietreiber</id>
      <name>Christian Krause</name>
      <url>https://github.com/wookietreiber</url>
    </developer>
  </developers>
)

// -------------------------------------------------------------------------------------------------
// publishing
// -------------------------------------------------------------------------------------------------

publishMavenStyle := true

publishTo <<= version { (version: String) ⇒
  val nexus = "https://oss.sonatype.org/"
  if (version.trim.endsWith("-SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ ⇒ false }

