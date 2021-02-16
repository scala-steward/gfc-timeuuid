import scoverage.ScoverageKeys

name := "gfc-timeuuid"

organization := "org.gfccollective"

scalaVersion := "2.13.4"

crossScalaVersions := Seq(scalaVersion.value, "2.12.12")

scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.3" % Test,
  "org.scalacheck" %% "scalacheck" % "1.15.2" % Test,
  "org.apache.cassandra" % "cassandra-all" % "3.11.10" % Test,
  "com.datastax.oss" % "java-driver-core" % "4.10.0" % Test,
  "com.netflix.astyanax" % "astyanax" % "3.10.2" % Test,
)

ScoverageKeys.coverageFailOnMinimum := true

ScoverageKeys.coverageMinimum := 84.2

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("Apache-style" -> url("https://raw.githubusercontent.com/gfc-collective/gfc-timeuuid/master/LICENSE"))

homepage := Some(url("https://github.com/gfc-collective/gfc-timeuuid"))

pomExtra := (
  <scm>
    <url>https://github.com/gfc-collective/gfc-timeuuid.git</url>
    <connection>scm:git:git@github.com:gfc-collective/gfc-timeuuid.git</connection>
  </scm>
  <developers>
    <developer>
      <id>pbarron</id>
      <name>Peter Barron</name>
      <url>https://github.com/pbarron</url>
    </developer>
  </developers>
)
