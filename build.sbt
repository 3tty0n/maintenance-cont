lazy val commonSettings = Seq(
  organization := "com.github.3tty0n",
  scalaVersion := "2.11.8",
  version      := "0.1.0-SNAPSHOT",
  libraryDependencies ++= allDependencies,
  scalacOptions       ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint", "-language:_"),
  resolvers           += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

lazy val allDependencies = {
  lazy val playVersion = "2.5.12"
  Seq(
    "org.scalatest" %% "scalatest" % "3.0.1",
    "org.scalaz" %% "scalaz-core" % "7.2.8",
    "com.typesafe.play" %% "play" % playVersion,
    "com.typesafe.play" %% "play-test" % playVersion % "test"
  )
}

lazy val root = (project in file(".")).
  settings(
    commonSettings: _*
  ).
  settings(
    name := "maintenance-action-cont"
  ).
  aggregate(
    action,
    maintenance
  )

lazy val action = (project in file("action-cont")).
  settings(
    commonSettings: _*
  ).
  settings(
    name := "action-cont"
  )

lazy val maintenance = (project in file("maintenance-cont")).
  settings(
    commonSettings: _*
  ).
  settings(
    name := "maintenance-cont"
  ).
  dependsOn(
    action
  )
