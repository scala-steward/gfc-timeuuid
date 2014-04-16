package com.gilt

import java.util.{Date, UUID}
import scala.language.implicitConversions

package object timeuuid {
  implicit def timeUuidToDate(value: UUID): Date = new TimeUuidToDate().extract(value)

  implicit def timeUuidToUnixTimestamp(value: UUID): Long = new TimeUuidToUnixTimeStamp().extract(value)

  implicit def timeUuidToBytes(value: UUID): Array[Byte] = new TimeUuidToBytes().extract(value)

  implicit def bytesToTimeUuid(value: Array[Byte]) = new BytesToTimeUuid().extract(value)
}
