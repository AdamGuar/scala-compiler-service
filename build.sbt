name := """scala-compiler-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "junit" % "junit" % "4.8.1" % "test",
  "info.debatty" % "java-string-similarity" % "0.24"
  
)

