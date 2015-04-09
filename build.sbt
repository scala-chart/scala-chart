import scalax.chart.build._

import Dependencies._

lazy val chart = (
  ChartProject("scala-chart", ".")
  settings (
    autoAPIMappings := true,
    apiURL := Some(url(s"""http://wookietreiber.github.io/scala-chart/${version.value}/api/""")),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    scalacOptions in Test ++= Seq("-Yrangepos"),
    libraryDependencies ++= Seq(jfreechart, Swing(scalaVersion.value)),
    libraryDependencies ++= Specs2(scalaVersion.value).map(_ % "test"),
    libraryDependencies += itext % Optional,
    libraryDependencies += jfreesvg % Optional,
    initialCommands in (Compile, consoleQuick) <<= initialCommands in Compile,
    initialCommands in Compile in console += """
      import scalax.chart._
      import scalax.chart.api._
    """,
    scalacOptions in (Compile, doc) <++= (baseDirectory) map { bd =>
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", docURL)
    }
  )
)
