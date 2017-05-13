name := "gfc-timeuuid"

organization := "com.gilt"

scalaVersion := "2.11.11"

crossScalaVersions := Seq(scalaVersion.value, "2.12.2", "2.10.6")

scalacOptions += "-target:jvm-1.7"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.1" % "test",
  "com.netflix.astyanax" % "astyanax" % "1.56.48" % "test"
)

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

licenses := Seq("Apache-style" -> url("https://raw.githubusercontent.com/gilt/gfc-timeuuid/master/LICENSE"))

homepage := Some(url("https://github.com/gilt/gfc-timeuuid"))

pomExtra := (
  <scm>
    <url>https://github.com/gilt/gfc-timeuuid.git</url>
    <connection>scm:git:git@github.com:gilt/gfc-timeuuid.git</connection>
  </scm>
  <developers>
    <developer>
      <id>pbarron</id>
      <name>Peter Barron</name>
      <url>https://github.com/pbarron</url>
    </developer>
  </developers>
)
