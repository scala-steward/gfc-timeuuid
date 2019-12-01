package com.gilt.timeuuid

import org.scalatest.Assertions
import org.scalatest.concurrent.Conductors
import org.scalatest.time.{Span, Seconds}
import org.scalatest.funsuite.AnyFunSuite

class ClockTest extends AnyFunSuite with Assertions with Conductors {
  override implicit def patienceConfig = PatienceConfig(timeout = Span(10L, Seconds))

  test("get time") {
    val currentTime = (System.currentTimeMillis - Clock.StartEpoch) * 10000
    val time = Clock.time()
    assert(time / 10000000 === currentTime / 10000000) //fuzzy to 10 milli secs
  }

  test("is unique") {
    val timestamps = Stream.continually(Clock.time())
    timestamps.zip(timestamps.tail).map(ab => assert(ab._1 != ab._2)).take(1000000)
  }

  test("is sequential") {
    val timestamps = Stream.continually(Clock.time())
    timestamps.zip(timestamps.tail).map(ab => assert(ab._1 < ab._2)).take(100000)
  }

  test("is unique in parallel") {
    val conductor = new Conductor
    import conductor._

    import scala.collection.JavaConverters._
    val result = new java.util.concurrent.ConcurrentHashMap[Int, Seq[Long]]().asScala

    val threads = 20
    val count = 5000

    1 to threads foreach {
      x =>
        threadNamed("consumer " + x) {
          result.put(x, Seq.fill(count)(Clock.time()))
        }
    }

    whenFinished {
      assert(result.size === threads)
      result.foreach {
        case (idx , seq) =>
          assert(seq.toSet.size === count)
          assert(seq === seq.sorted)
      }

      val uuids = result.values.flatten.toSet
      assert(uuids.size === threads * count)
    }
  }
}
