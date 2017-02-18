package com.github.micchon.app.request

sealed trait MaintenanceRequest
case object UnderMaintenance extends MaintenanceRequest
case object NotUnderMaintenance extends MaintenanceRequest
