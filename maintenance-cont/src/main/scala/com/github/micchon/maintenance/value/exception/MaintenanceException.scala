package com.github.micchon.maintenance.value.exception

class MaintenanceException(
  message: String = null,
  cause: Throwable = null
) extends RuntimeException(message, cause)
