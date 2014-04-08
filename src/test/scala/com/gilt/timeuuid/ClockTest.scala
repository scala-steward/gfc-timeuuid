package com.gilt.timeuuid

import org.scalatest.{Assertions, FunSuite}
import org.scalatest.concurrent.Conductors
import scala.collection.mutable

class ClockTest extends FunSuite with Assertions with Conductors {

  test("get time") {
    val currentTime = (System.currentTimeMillis - Clock.START_EPOCH) * 10000
    val time = Clock().time()
    assert(time / 100000 === currentTime / 100000) //fuzzy to the millis sec
  }

  test("is unique") {
    val clock = Clock()
    val times = Seq.fill(1000000)(clock.time()).toSet
    assert(times.size === 1000000)
  }

  test("is sequential") {
    val clock = Clock()
    val times = Seq.fill(1000000)(clock.time())
    assert(times === times.sorted)
  }

  test("is unique in parallel") {
    val conductor = new Conductor
    import conductor._

    val result = mutable.Map[Int, Seq[Long]]()

    1 to 20 foreach (x => thread("consumer " + x) {
      val clock = Clock()
      val times = Seq.fill(10000)(clock.time())
      result.put(x, times)
    })

    whenFinished{
      assert(result.size === 20)
      result.foreach{(entry: (Int, Seq[Long])) =>
        assert(entry._2.size === 10000)
        assert(entry._2 === entry._2.sorted)
      }

      val uuids = result.values.flatten.toSet
      assert(uuids.size === 200000)
    }
  }
}
