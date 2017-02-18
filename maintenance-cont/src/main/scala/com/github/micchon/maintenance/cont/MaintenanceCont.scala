package com.github.micchon.maintenance.cont

import java.io.File

import com.github.micchon.cont.ActionCont
import com.github.micchon.maintenance.request.{MaintenanceRequest, NotUnderMaintenance}
import com.github.micchon.maintenance.value.exception.MaintenanceException
import com.github.micchon.maintenance.value.{MaintenanceSpecifics, Operation}
import play.api.mvc.{AnyContent, Request, Result, Results}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MaintenanceCont {

  def apply[A](operation: Operation*)(implicit request: Request[A]): ActionCont[Seq[MaintenanceRequest]] =
    ActionCont.fromFuture(
      Future.sequence(operation.map(isUnderMaintenance(_).flatMap {
        case false => Future.successful(NotUnderMaintenance)
        case true => Future.failed(new MaintenanceException)
      }
    )))

  def flow(operation: Operation*)(implicit request: Request[AnyContent]): ActionCont[Request[AnyContent]] =
    ActionCont((f: Request[AnyContent] => Future[Result]) =>
      Future.sequence(operation.map(isUnderMaintenance)).flatMap {
        case result if result.forall(_ == false) => f(request)
        case _ => Future.successful(Results.ServiceUnavailable)
      }
    )

  private def isUnderMaintenance(operation: Operation): Future[Boolean] = {
    val file = new File(s"${MaintenanceSpecifics().filePath}/${operation.name}.${MaintenanceSpecifics().extension}")
    Future(file.exists())
  }
}
