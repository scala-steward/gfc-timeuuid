name := "gfc-timeuuid"

organization := "com.gilt"

scalaVersion := "2.12.7"

crossScalaVersions := Seq(scalaVersion.value, "2.11.12")

scalacOptions += "-target:jvm-1.7"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.2" % Test,
  "com.netflix.astyanax" % "astyanax" % "3.10.2" % Test
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
