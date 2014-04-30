package com.gilt.timeuuid

import java.net.{InetAddress, NetworkInterface}
import scala.util.Try

private[timeuuid] object Node {
  private lazy val instance = new MacAddressNode()

  def apply() = instance
}

private[timeuuid] trait Node {
  def id: Long
}

private[timeuuid] class MacAddressNode extends Node {

  private val nodeId =
    for {
      localhost <- Try(InetAddress.getLocalHost)
      localNetworkInterface <- Try(NetworkInterface.getByInetAddress(localhost))
      localMacAddress <- Try(localNetworkInterface.getHardwareAddress)
      nodeId <- Try(convertMacAddressToLong(localMacAddress))
    } yield nodeId


  override val id: Long = nodeId.getOrElse(throw new IllegalStateException("Unable to determine the Node Id. Mac address is not available."))

  private def convertMacAddressToLong(macAddress: Array[Byte]): Long = {
    require(macAddress.length == 6)
    var node = 0L
    for ((byte, index) <- macAddress.zipWithIndex)
      node |= (0x00000000000000ffL & byte.asInstanceOf[Long]) << (index * 8)
    node
  }
}