
name := "sfreechart"

version := "0.1.0-SNAPSHOT"

organization := "org.sfree"

scalaVersion := "2.10.0-RC1"

crossScalaVersions := Seq ( "2.8.2", "2.9.2", "2.10.0-RC1" )

libraryDependencies += "org.jfree" % "jfreechart" % "1.0.14"

libraryDependencies <+= scalaVersion { sv ⇒
  "org.scala-lang" % "scala-swing" % sv
}

libraryDependencies <+= scalaVersion { _ match {
  case "2.8.2"      ⇒ "org.specs2" %% "specs2" % "1.5"    % "test"
  case "2.9.2"      ⇒ "org.specs2" %% "specs2" % "1.12.2" % "test"
  case "2.10.0-RC1" ⇒ "org.specs2" %% "specs2" % "1.12.2" % "test" cross CrossVersion.full
}}

scalacOptions <++= scalaVersion map { sv ⇒
  if (sv.startsWith("2.10"))
    Seq("-language:implicitConversions", "-language:reflectiveCalls")
  else
    Nil
}

