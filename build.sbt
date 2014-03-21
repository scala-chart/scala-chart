import scalax.chart.build._

import Dependencies._

lazy val chart = (
  ChartProject("scala-chart", ".")
  settings (
    libraryDependencies ++= {
      val sv = scalaVersion.value
      val swingDep = if (sv startsWith "2.10")
        "org.scala-lang" % "scala-swing" % sv
      else
        "org.scala-lang.modules" %% "scala-swing" % "1.0.1"
      val specsDep = if (sv startsWith "2.10")
        specs2 % "2.2.3"
      else
        specs2 % "2.3.10"
      Seq(swingDep, jfreechart, specsDep % "test")
    },
    scalacOptions in (Compile, doc) <++= (baseDirectory) map { bd =>
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", docURL)
    }
  )
)
