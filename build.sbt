gilt.GiltProject.jarSettings

name := "commons-timeuuid"

scalaVersion := "2.10.3"

// annoying issue with pickling in sonatype's repo
checksums in update := Nil

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.3" % "test",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.1" % "test",
  "org.apache.cassandra" % "cassandra-all" % "1.2.9" % "test"
)
