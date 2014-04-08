package com.gilt.timeuuid

import java.util.concurrent.atomic.AtomicLong
import scala.annotation.tailrec

private[timeuuid] object Clock {
  // Millis at 00:00:00.000 15 Oct 1582.
  private[timeuuid] val START_EPOCH: Long = -12219292800000L
  private lazy val instance = new HundredsOfNanosClock()

  def apply() = instance //singleton
}

private[timeuuid] trait Clock {
  def time(): Long
}

// This is based on Astyanax Clocks and Datastax approach to generating TimeUuids
private[timeuuid] class HundredsOfNanosClock extends Clock {

  import Clock._

  private val lastTimestamp = new AtomicLong(0L)

  override def time(): Long = uniqueTime(nextTimestamp())

  @tailrec
  private def uniqueTime(candidate: Long): Long = {
    val last = lastTimestamp.get()
    if (candidate > last) {
      if (lastTimestamp.compareAndSet(last, candidate))
        candidate
      else
        uniqueTime(nextTimestamp()) //another thread beat us to it. try again.
    }else if (millisOf(candidate) == millisOf(last)) // Try next id if we have not generated more than 10K uuid on the same millis second
      uniqueTime(last + 1)
    else if (millisOf(candidate) < millisOf(last))
      lastTimestamp.incrementAndGet() //Clock went back in time. Keep moving.. just increment the lastTimestamp.
    else
      uniqueTime(nextTimestamp())
  }

  private def millisOf(timestamp: Long): Long = timestamp / 10000

  private def nextTimestamp(): Long = (System.currentTimeMillis - START_EPOCH) * 10000
}
