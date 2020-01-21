package org.gfccollective

import java.util.{Date, UUID}
import java.nio.ByteBuffer

package object timeuuid {
  implicit class UuidConversions(from: UUID) {
    require(from.version() == 1, "Version 1 uuid required")

    def toLong: Long = from.timestamp() / 10000 + Clock.StartEpoch

    def toDate: Date = new Date((from.timestamp() / 10000) + Clock.StartEpoch)

    def toBytes: Array[Byte] = ByteBuffer.wrap(new Array[Byte](2 * 8))
      .putLong(from.getMostSignificantBits)
      .putLong(from.getLeastSignificantBits)
      .array()
  }

  implicit class ByteArrayConversions(from: Array[Byte]) {
    require(from.length == 16, s"Expected 16 bytes, got ${from.mkString(",")}")

    def toUUID: UUID = {
      val buffer = ByteBuffer.wrap(from)
      new UUID(buffer.getLong, buffer.getLong)
    }
  }
}
