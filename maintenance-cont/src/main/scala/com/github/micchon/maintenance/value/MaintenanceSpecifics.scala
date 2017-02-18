package com.github.micchon.maintenance.value

import scala.reflect.io.Path

case class MaintenanceSpecifics(
  filePath: Path = Path("resources/maintenance/operation"),
  extension: String = "maintenance"
)
