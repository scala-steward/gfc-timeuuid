package com.gilt.timeuuid

import org.scalatest.{Assertions, FunSuite}

class NodeTest extends FunSuite with Assertions {

  test("generate node id") {
    assert(Node.id != 0)
  }

  test("generate node id is always the same") {
    assert(Node.id == Node.id)
  }

  test("multicast bit set on localhost network interface approach") {
    assert((new LocalHostAndNetworkInterfacesNode().id.get & (1L << 40)) != 0)
  }
}
