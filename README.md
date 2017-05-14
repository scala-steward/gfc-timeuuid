# gfc-timeuuid [![Build Status](https://secure.travis-ci.org/gilt/gfc-timeuuid.png)](http://travis-ci.org/gilt/gfc-timeuuid) [![Join the chat at https://gitter.im/gilt/gfc](https://badges.gitter.im/gilt/gfc.svg)](https://gitter.im/gilt/gfc?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

An Scala timeuuid library for generating version 1 UUIDs, based on http://www.ietf.org/rfc/rfc4122.txts. Part of the [Gilt Foundation Classes](https://github.com/gilt?q=gfc).

## Getting gfc-timeuuid

The latest version is 0.0.8, which is cross-built against Scala 2.10.x, 2.11.x and 2.12.x.

If you're using SBT, add the following line to your build file:

```scala
libraryDependencies += "com.gilt" %% "gfc-timeuuid" % "0.0.8"
```

For Maven and other build tools, you can visit [search.maven.org](http://search.maven.org/#search%7Cga%7C1%7Ccom.gilt%20gfc).
(This search will also list other available libraries from the gilt fundation classes.)

## Contents and Example Usage

Generates a new unique time based UUID

    val uuid = TimeUuid()

Convert time UUID to unix timestamp

    import com.gilt.timeuuid._

    val uuid = TimeUuid()
    val timestamp = uuid.toLong

Convert time UUID to unix date

    import com.gilt.timeuuid._

    val uuid = TimeUuid()
    val date = uuid.toDate

Convert arrays of 16 bytes to UUID:

    val bytes = new Array[Byte](16)
    val uuid = bytes.toUUID

## License
Copyright 2014 Gilt Groupe, Inc.

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
