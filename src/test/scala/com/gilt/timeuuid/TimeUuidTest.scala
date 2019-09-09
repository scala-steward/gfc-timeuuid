package com.gilt.timeuuid

import org.scalatest.{Assertions, FunSuite}
import java.util.UUID

import com.datastax.driver.core.utils.UUIDs
import com.datastax.oss.driver.api.core.uuid.Uuids
import org.apache.cassandra.utils.UUIDGen
import com.netflix.astyanax.serializers.TimeUUIDSerializer

class TimeUuidTest extends FunSuite with Assertions {

  test("Generation timeUuid") {
    val timestamp = (System.currentTimeMillis - Clock.StartEpoch) * 10000
    val uuid: UUID = TimeUuid()
    assert(uuid.version() === 1)
    assert(uuid.variant() === 2)
    assert(uuid.timestamp() >= timestamp)
    assert(uuid.clockSequence() != 0)
  }

  test("timestamps are sequential") {
    val uuids = Seq.fill(100000)(TimeUuid()).map(_.timestamp())
    assert(uuids.sorted === uuids)
  }

  test("Generation timeUuid with unix timestamp") {
    val sysTimestamp = System.currentTimeMillis
    val uuid: UUID = TimeUuid(sysTimestamp)
    val timestamp = (sysTimestamp - Clock.StartEpoch) * 10000
    assert(uuid.version() === 1)
    assert(uuid.variant() === 2)
    assert(uuid.timestamp() === timestamp)
    assert(uuid.clockSequence() != 0)
  }

  test("Comparability with Datastax timeUUID generation") {
    val datastax = Uuids.timeBased()
    val uuid: UUID = TimeUuid()
    assert(datastax.version() === uuid.version())
    assert(datastax.variant() === uuid.variant())
    assert(datastax.timestamp() / 100000 === uuid.timestamp() / 100000) // this can only be a fuzzy test
  }

  test("Comparability with cassandra timeUUID generation") {
    val cassandra = UUIDGen.getTimeUUID
    val uuid: UUID = TimeUuid()
    assert(cassandra.version() === uuid.version())
    assert(cassandra.variant() === uuid.variant())
    assert(cassandra.timestamp() / 100000 === uuid.timestamp() / 100000) // this can only be a fuzzy test
  }

  test("Comparability with Datastax timeUUID generation based on timestamp") {
    val sysTimestamp = System.currentTimeMillis
    val uuid: UUID = TimeUuid(sysTimestamp)
    assert(UUIDs.unixTimestamp(uuid) === sysTimestamp)
  }

  test("Comparability with cassandra timeUUID generation on timestamp") {
    val sysTimestamp = System.currentTimeMillis
    val uuid: UUID = TimeUuid(sysTimestamp)
    assert(UUIDGen.getAdjustedTimestamp(uuid) === sysTimestamp)
  }

  test("Convert UUID to unix timestamp") {
    import com.gilt.timeuuid._
    val sysTimestamp = System.currentTimeMillis
    val uuid: UUID = TimeUuid(sysTimestamp)
    val timestamp = uuid.toLong
    assert(timestamp === sysTimestamp)
  }

  test("Convert UUID to unix date") {
    import com.gilt.timeuuid._
    val sysTimestamp = System.currentTimeMillis
    val uuid: UUID = TimeUuid(sysTimestamp)
    val date = uuid.toDate
    assert(date.getTime === sysTimestamp)
  }

  test("Convert UUID to bytes") {
    import com.gilt.timeuuid._
    val uuid: UUID = TimeUuid()
    val bytes = uuid.toBytes

    assert(TimeUUIDSerializer.get().toBytes(uuid) === bytes)
  }

  test("Convert bytes to UUID") {
    import com.gilt.timeuuid._
    val uuid = TimeUuid()
    val bytes = uuid.toBytes
    val result: UUID = bytes.toUUID

    assert(result === uuid)
  }
}
