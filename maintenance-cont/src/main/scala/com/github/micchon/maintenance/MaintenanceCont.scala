package com.github.micchon.maintenance

import java.io.File

import com.github.micchon.cont.ActionCont
import play.api.mvc.Request

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MaintenanceCont {

  def apply[A](operation: Operation*)(implicit request: Request[A]): ActionCont[MaintenanceRequest] =
    ActionCont(f =>
      Future.sequence(operation.map(isUnderMaintenance)).flatMap {
        case results if results.forall(_ == false) => f(NotUnderMaintenance)
        case _ => f(UnderMaintenance)
      }
    )

  private def isUnderMaintenance(operation: Operation): Future[Boolean] = {
    val file = new File(s"${MaintenanceSpecifics().filePath}/${operation.name}.${MaintenanceSpecifics().extension}")
    Future(file.exists())
  }
}
