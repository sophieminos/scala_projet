val zioVersion = "2.0.20"
val scala3Version = "3.3.1"
val scalaCsvVersion = "1.3.10"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala_projet",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"         % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion,
      "com.github.tototoshi" %% "scala-csv" % scalaCsvVersion
    ),
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
