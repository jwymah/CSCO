name := "CSCO"

version := "0.1"

scalaVersion := "2.13.1"

val circeVersion = "0.12.1"

libraryDependencies ++= Seq(
  "org.scalatest"   %% "scalatest" % "3.0.8"   % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "com.typesafe.akka"     %% "akka-actor" % "2.5.23",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser"% circeVersion
)