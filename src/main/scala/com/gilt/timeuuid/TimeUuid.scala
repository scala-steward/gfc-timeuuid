package com.gilt.timeuuid

import java.util.{UUID, Random}

//Based on http://www.ietf.org/rfc/rfc4122.txt
object TimeUuid {
  private lazy val generator = new TimeUuidGenerator()
  def apply(): UUID = generator.build()
  def apply(timeInMillis: Long) = generator.build(timeInMillis)
}

//Based on Datastax/Cassandra/Astyanax timeUUID generation
private[timeuuid] class TimeUuidGenerator {
  private lazy val node = Node()
  private lazy val clock = Clock()
  private lazy val clockSeqAndNode = buildClockSeqAndNode()

  def build(): UUID = new UUID(buildTime(clock.time()), clockSeqAndNode)

  def build(timeInMillis: Long): UUID = new UUID(buildTime(convertToNanos(timeInMillis)), clockSeqAndNode)

  private def convertToNanos(timeInMillis: Long): Long = (timeInMillis - Clock.START_EPOCH) * 10000

  private def buildTime(time: Long): Long = {
    var msb: Long = 0L
    msb |= (0x00000000ffffffffL & time) << 32
    msb |= (0x0000ffff00000000L & time) >>> 16
    msb |= (0x0fff000000000000L & time) >>> 48
    msb |= 0x0000000000001000L                  //Version 1 Uuid
    msb
  }

  private def buildClockSeqAndNode(): Long = {
    val clock: Long = new Random(System.currentTimeMillis).nextLong
    var lsb: Long = 0
    lsb |= (clock & 0x0000000000003FFFL) << 48  // clock sequence (14 bits)
    lsb |= 0x8000000000000000L                  // variant (2 bits)
    lsb |= node.id                              // 6 bytes
    lsb
  }
}