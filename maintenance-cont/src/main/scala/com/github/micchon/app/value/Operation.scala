package com.github.micchon.maintenance.value

object Operation {
  case object Transaction extends Operation("transaction")
  case object Client extends Operation("client")
  case object Agency extends Operation("agency")
}

sealed abstract class Operation(val name: String)
