
name := "sfreechart"

version := "0.1.0-SNAPSHOT"

organization := "org.sfree"

scalaVersion := "2.9.2"

crossScalaVersions := Seq ( "2.8.2", "2.9.2", "2.10.0-M7" )

libraryDependencies ++= Seq (
  "org.jfree"  %  "jfreechart" % "1.0.14",
  "org.specs2" %% "specs2"     % "1.12.1" % "test"
)

libraryDependencies <+= scalaVersion { sv ⇒
  "org.scala-lang" % "scala-swing" % sv
}

scalacOptions <<= (scalaVersion, scalacOptions) map { (sv, opts) ⇒
  if (sv.startsWith("2.10"))
    opts ++ Seq("-language:implicitConversions")
  else
    opts
}

