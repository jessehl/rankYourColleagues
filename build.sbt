import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0"
ThisBuild / organizationName := "AP"

lazy val root = (project in file("."))
  .settings(
    name := "christmas_rating",
    libraryDependencies += scalaTest % Test
  )


