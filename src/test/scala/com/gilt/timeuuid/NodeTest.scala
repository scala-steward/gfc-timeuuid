package com.gilt.timeuuid

import org.scalatest.{Assertions, FunSuite}

class NodeTest extends FunSuite with Assertions {

  test("generate node id from mac") {
    assert(Node.id != 0)
  }
}
