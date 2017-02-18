package com.github.micchon.maintenance.value

object MaintenanceConfig {
  case object FilePath extends MaintenanceConfig("resources/maintenance/operation")
  case object Extension extends MaintenanceConfig("maintenance")
}

sealed abstract class MaintenanceConfig(val value: String)