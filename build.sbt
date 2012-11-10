
name := "sfreechart"

version := "0.1.0-SNAPSHOT"

organization := "org.sfree"

scalaVersion := "2.10.0-RC2"

libraryDependencies ++= Seq (
  "org.jfree"  %  "jfreechart" % "1.0.14",
  "org.specs2" %% "specs2"     % "1.12.2" % "test" cross CrossVersion.full
)

