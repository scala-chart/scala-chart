package scalax.chart
package build

import sbt._
import Keys._

object Publishing {
  val publishing = Seq (
    publishMavenStyle := true,
    publishTo <<= version { (version: String) ⇒
      val nexus = "https://oss.sonatype.org/"
      if (version.trim.endsWith("-SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ ⇒ false }
  )
}
