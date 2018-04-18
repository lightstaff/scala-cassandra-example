name := "scala-cassandra-example"

version := "0.1"

scalaVersion := "2.12.5"

val phantomVersion = "2.24.2"

libraryDependencies ++= Seq(
  "com.outworkers"  %% "phantom-dsl" % phantomVersion,
  "com.outworkers"  %% "phantom-connectors" % phantomVersion,
  "com.typesafe" % "config" % "1.3.3",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

mainClass in Compile := Some("com.example.Main")

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:_",
  "-Xlint",
  "-Xfatal-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard"
)