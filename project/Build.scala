import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "ac.uk.bbk",
    version := "1.0.0",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.11.0", "2.11.1", "2.11.2", "2.11.3", "2.11.4", "2.11.5", "2.11.6", "2.11.7"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    scalacOptions ++= Seq(),
    scalacOptions in Test ++= Seq("-Yrangepos")
  )

  val dependencies = Seq(
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3",
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.4" % "test"
  )

  val applicationExecutable = Seq(
    mainClass in (Compile,run) := Some("ac.uk.bbk.sdp.mastermind.Mastermind")
  )
}

object ProjectBuild extends Build {

  import BuildSettings._

  lazy val root: Project = Project(
    "sdp-mastermind-exercise",
    file("."),
    settings = buildSettings ++ dependencies ++ applicationExecutable ++ Seq(
      run <<= run in Compile
    )
  )
}
