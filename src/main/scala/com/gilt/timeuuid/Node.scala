package com.gilt.timeuuid

import java.net.{InetAddress, NetworkInterface}

private[timeuuid] object Node {
  private lazy val instance = new MacAddressNode()
  def apply() = instance
}

private[timeuuid] trait Node {
  def id: Long
}

private[timeuuid] class MacAddressNode extends Node {
  private val localhost = InetAddress.getLocalHost
  private val localNetworkInterface = NetworkInterface.getByInetAddress(localhost)
  private val localMacAddress = localNetworkInterface.getHardwareAddress
  private val nodeId = convertMacAddressToLong()

  override val id: Long = nodeId

  private def convertMacAddressToLong(): Long = {
    require(localMacAddress.length == 6)
    var node = 0L
    for ((byte, index) <- localMacAddress.zipWithIndex)
      node |= (0x00000000000000ffL & byte.asInstanceOf[Long]) << (index * 8)
    node
  }
}