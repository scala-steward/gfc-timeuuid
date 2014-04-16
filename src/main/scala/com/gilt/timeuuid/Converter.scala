package com.gilt.timeuuid

import java.util.{Date, UUID}
import java.nio.ByteBuffer

trait Converter[F, T] {
  def extract(from: F): T
}

class TimeUuidToDate extends Converter[UUID, Date] {
  override def extract(from: UUID): Date = {
    require(from.version() == 1, "Version 1 uuid required")
    new Date((from.timestamp() / 10000) + Clock.START_EPOCH)
  }
}

class TimeUuidToUnixTimeStamp extends Converter[UUID, Long] {
  override def extract(from: UUID): Long = {
    require(from.version() == 1, "Version 1 uuid required")
    (from.timestamp() / 10000) + Clock.START_EPOCH
  }
}

class TimeUuidToBytes extends Converter[UUID,Array[Byte]] {
  override def extract(from: UUID): Array[Byte] =
    ByteBuffer.wrap(new Array[Byte](2 * 8))
      .putLong(from.getMostSignificantBits)
      .putLong(from.getLeastSignificantBits)
      .array()
}

class BytesToTimeUuid extends Converter[Array[Byte], UUID] {
  override def extract(bytes: Array[Byte]): UUID = {
    require(bytes.length == 16)
    val buffer = ByteBuffer.wrap(bytes)
    new UUID(buffer.getLong, buffer.getLong)
  }
}
