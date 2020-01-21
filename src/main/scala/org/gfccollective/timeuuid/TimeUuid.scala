package org.gfccollective.timeuuid

import java.util.{UUID, Random}

//Based on http://www.ietf.org/rfc/rfc4122.txt & Datastax/Cassandra/Astyanax timeUUID generation
object TimeUuid {

  private[this] val clockSeqAndNode = buildClockSeqAndNode()

  def apply(): UUID = new UUID(buildTime(Clock.time()), clockSeqAndNode)

  /*
    This method does not create a unique Version 1 UUID. In many ways it goes against 
    the general principle of a time based uuid. It is here primarily generate test UUIDs.  
  */
  def apply(timeInMillis: Long): UUID = new UUID(buildTime(convertToNanos(timeInMillis)), clockSeqAndNode)

  private def convertToNanos(timeInMillis: Long): Long = (timeInMillis - Clock.StartEpoch) * 10000

  private def buildTime(time: Long): Long = {
    var msb: Long = 0L
    msb |= (0x00000000ffffffffL & time) << 32
    msb |= (0x0000ffff00000000L & time) >>> 16
    msb |= (0x0fff000000000000L & time) >>> 48
    msb |= 0x0000000000001000L //Version 1 Uuid
    msb
  }

  private def buildClockSeqAndNode(): Long = {
    val clock: Long = new Random(System.currentTimeMillis).nextLong
    var lsb: Long = 0
    lsb |= (clock & 0x0000000000003FFFL) << 48 // clock sequence (14 bits)
    lsb |= 0x8000000000000000L // variant (2 bits)
    lsb |= Node.id // 6 bytes
    lsb
  }
}

