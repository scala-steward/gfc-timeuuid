package com.gilt.timeuuid

import java.util.{Date, UUID}
import java.nio.ByteBuffer

trait Converter[T] {
  def extract(from: UUID): T
}

class TimeUuidToDate extends Converter[Date] {
  override def extract(from: UUID): Date = {
    require(from.version() == 1, "Version 1 uuid required")
    new Date((from.timestamp() / 10000) + Clock.START_EPOCH)
  }
}

class TimeUuidToUnixTimeStamp extends Converter[Long] {
  override def extract(from: UUID): Long = {
    require(from.version() == 1, "Version 1 uuid required")
    (from.timestamp() / 10000) + Clock.START_EPOCH
  }
}

class TimeUuidToBytes extends Converter[Array[Byte]] {
  override def extract(from: UUID): Array[Byte] = {
    val buf = ByteBuffer.wrap(new Array[Byte](2 * 8))
    buf.putLong(from.getMostSignificantBits)
    buf.putLong(from.getLeastSignificantBits)
    ByteBuffer.wrap(new Array[Byte](2 * 8)).array()
  }
}
