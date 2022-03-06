val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "IT5100A",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq (
      "mysql" % "mysql-connector-java" % "8.0.27",
      "org.apache.commons" % "commons-dbcp2" % "2.8.0",
      "org.typelevel" %% "cats-effect" % "3.3.6" withSources() withJavadoc(),
    )
  )
