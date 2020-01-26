# gfc-timeuuid [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.gfccollective/gfc-timeuuid_2.12/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/org.gfccollective/gfc-timeuuid_2.12) [![Build Status](https://github.com/gfc-collective/gfc-timeuuid/workflows/Scala%20CI/badge.svg)](https://github.com/gfc-collective/gfc-timeuuid/actions) [![Coverage Status](https://coveralls.io/repos/gfc-collective/gfc-timeuuid/badge.svg?branch=master&service=github)](https://coveralls.io/github/gfc-collective/gfc-timeuuid?branch=master)

An Scala timeuuid library for generating version 1 UUIDs, based on http://www.ietf.org/rfc/rfc4122.txts.
A fork and new home of the now unmaintained Gilt Foundation Classes (`com.gilt.gfc`), now called the [GFC Collective](https://github.com/gfc-collective), maintained by some of the original authors.


## Getting gfc-timeuuid

The latest version is 1.0.0, released on 21/Jan/2020 and cross-built against Scala 2.12.x and 2.13.x.

If you're using SBT, add the following line to your build file:

```scala
libraryDependencies += "org.gfccollective" %% "gfc-timeuuid" % "1.0.0"
```

For Maven and other build tools, you can visit [search.maven.org](http://search.maven.org/#search%7Cga%7C1%7Corg.gfccollective).
(This search will also list other available libraries from the GFC Collective.)

## Contents and Example Usage

Generates a new unique time based UUID

    val uuid = TimeUuid()

Convert time UUID to unix timestamp

    import org.gfccollective.timeuuid._

    val uuid = TimeUuid()
    val timestamp = uuid.toLong

Convert time UUID to unix date

    import org.gfccollective.timeuuid._

    val uuid = TimeUuid()
    val date = uuid.toDate

Convert arrays of 16 bytes to UUID:

    val bytes = new Array[Byte](16)
    val uuid = bytes.toUUID

## License

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
