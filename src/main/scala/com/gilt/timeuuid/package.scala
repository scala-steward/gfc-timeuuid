package com.gilt

import java.util.{Date, UUID}

package object timeuuid {
  implicit def timeUuidToDate(value: UUID): Date = new TimeUuidToDate().extract(value)

  implicit def timeUuidToUnixTimestamp(value: UUID): Long = new TimeUuidToUnixTimeStamp().extract(value)
}
