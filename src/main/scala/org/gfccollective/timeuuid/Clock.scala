package org.gfccollective.timeuuid

import java.util.concurrent.atomic.AtomicLong
import scala.annotation.tailrec

// This is based on Astyanax Clocks and Datastax approach to generating TimeUuids
// e.g. https://github.com/Netflix/astyanax/blob/master/astyanax-core/src/main/java/com/netflix/astyanax/util/TimeUUIDUtils.java
private[timeuuid] object Clock {
  // Millis at 00:00:00.000 15 Oct 1582.
  val StartEpoch: Long = -12219292800000L

  private[this] val lastTimestamp = new AtomicLong(0L)

  def time(): Long = uniqueTime(nextTimestamp())

  @tailrec
  private def uniqueTime(candidate: Long): Long = {
    def millisOf(timestamp: Long): Long = timestamp / 10000

    val last = lastTimestamp.get()
    if (candidate > last) {
      if (lastTimestamp.compareAndSet(last, candidate)) {
        candidate
      } else {
        uniqueTime(nextTimestamp()) //another thread beat us to it. try again.
      }
    } else {
      val (candidateMillis, lastMillis) = (millisOf(candidate), millisOf(last))
      // Try next id if we have not generated more than 10K uuid on the same millis second
      // if we hanve't generated more than 10k uuid on the ms, try next id
      // otherwise, if the clock went back in time, just keep moving
      // otherwise just generate the next time
      if (candidateMillis == lastMillis) uniqueTime(last + 1)
      else if (candidateMillis < lastMillis) lastTimestamp.incrementAndGet()
      else uniqueTime(nextTimestamp())
    }
  }

  def nextTimestamp(): Long = (System.currentTimeMillis - Clock.StartEpoch) * 10000
}


