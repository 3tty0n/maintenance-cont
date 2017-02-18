package com.github.micchon.maintenance.request

sealed trait MaintenanceRequest
case object UnderMaintenance extends MaintenanceRequest
case object NotUnderMaintenance extends MaintenanceRequest
