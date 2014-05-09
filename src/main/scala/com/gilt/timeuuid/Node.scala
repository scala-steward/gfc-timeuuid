package com.gilt.timeuuid

import java.net.{InetAddress, NetworkInterface}
import scala.util.Try

private[timeuuid] object Node {
  lazy val id: Long = {
    for {
      localhost <- Try(InetAddress.getLocalHost)
      localNetworkInterface <- Try(NetworkInterface.getByInetAddress(localhost))
      localMacAddress <- Try(localNetworkInterface.getHardwareAddress)
      nodeId <- Try(convertMacAddressToLong(localMacAddress))
    } yield nodeId
  }.recover {
    case e: Throwable => throw new IllegalStateException("Unable to determine the Node Id. Mac address is not available.", e)
  }.get

  private def convertMacAddressToLong(macAddress: Array[Byte]): Long = {
    require(macAddress.length == 6, s"macAddress should be 6 bytes, was ${macAddress.mkString(",")}")
    val (result, _) = macAddress.foldLeft((0L, 0)) {
      case ((node, index), byte) =>
        (node | (0x00000000000000ffL & byte.asInstanceOf[Long]) << (index * 8), index + 1)
    }
    result
  }
}

