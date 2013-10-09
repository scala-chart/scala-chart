import scalax.chart.build._

import Dependencies._

lazy val chart = (
  ChartProject("scala-chart", ".")
  settings (
    libraryDependencies ++= Seq(swing % scalaVersion.value, jfreechart, specs2 % "test"),
    scalacOptions in (Compile, doc) <++= (baseDirectory) map { bd =>
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", docURL)
    }
  )
)
