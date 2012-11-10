
name := "sfreechart"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.0-RC2"

libraryDependencies ++= Seq (
  "org.jfree"  %  "jfreechart" % "1.0.14",
  "org.specs2" %% "specs2"     % "1.12.2" % "test" cross CrossVersion.full
)

initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile

initialCommands in Compile in console += """
  import org.sfree.chart.Charting._
"""

// -------------------------------------------------------------------------------------------------
// supplementary project information
// -------------------------------------------------------------------------------------------------

organization := "org.sfree"

description := "Scala Wrappers for JFreeChart"

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

