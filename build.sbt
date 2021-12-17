import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0"
ThisBuild / organizationName := "AP"

lazy val root = (project in file("."))
  .settings(
    name := "rankYourColleagues",
    libraryDependencies += ("com.novocode" % "junit-interface" % "0.11" % Test)
  )


