package scalax.chart
package build

import sbt._
import Keys._

object ProjectDescription {
  val projectDescription = Seq (
    description := "Scala Chart Library",
    homepage := Some(url("https://github.com/wookietreiber/scala-chart")),
    startYear := Some(2012),
    licenses := Seq (
      "GNU Lesser General Public Licence" → url("http://www.gnu.org/licenses/lgpl.txt")
    ),
    scmInfo := Some(ScmInfo(
      url("https://github.com/wookietreiber/scala-chart"),
      "scm:git:git://github.com/wookietreiber/scala-chart.git",
      Some("scm:git:https://github.com/wookietreiber/scala-chart.git")
    )),
    pomExtra := (
      <developers>
        <developer>
          <id>wookietreiber</id>
          <name>Christian Krause</name>
          <url>https://github.com/wookietreiber</url>
        </developer>
      </developers>
    )
  )
}
