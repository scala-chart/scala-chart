import sbt._
import Keys._

object ChartBuild extends Build {
  lazy val root = Project (
    id       = "scala-chart",
    base     = file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      name               := "sfreechart",
      organization       := "org.sfree",
      version            := "0.1.0-SNAPSHOT",
      scalaVersion       := "2.9.2",
      crossScalaVersions := Seq (
        "2.8.2",
        "2.9.0", "2.9.0-1",
        "2.9.1", "2.9.1-1",
        "2.9.2",
        "2.10.0-M6"
      ),
      libraryDependencies <++= scalaVersion { sv ⇒
        Seq (
          "org.jfree" % "jfreechart" % "1.0.14",
          "org.scala-lang" % "scala-swing" % sv
        )
      }
    )
  )
}
