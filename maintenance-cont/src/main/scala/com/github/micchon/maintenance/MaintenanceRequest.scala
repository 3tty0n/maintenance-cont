package com.github.micchon.maintenance

sealed trait MaintenanceRequest
case object UnderMaintenance extends MaintenanceRequest
case object NotUnderMaintenance extends MaintenanceRequest
