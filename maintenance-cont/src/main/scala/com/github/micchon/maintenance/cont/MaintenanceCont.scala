package com.github.micchon.maintenance.cont

import java.io.File

import com.github.micchon.cont.ActionCont
import com.github.micchon.maintenance.value.Operation
import com.github.micchon.maintenance.value.{MaintenanceConfig => Config}
import play.api.mvc.{AnyContent, Request, Result, Results}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MaintenanceCont {

  def flow(operation: Operation*)(implicit request: Request[AnyContent]): ActionCont[Request[AnyContent]] =
    ActionCont((f: Request[AnyContent] => Future[Result]) =>
      Future.sequence(operation.map(isUnderMaintenance)).flatMap {
        case result if result.forall(_ == false) => f(request)
        case _ => Future.successful(Results.ServiceUnavailable)
      }.recoverWith {
        case _ => Future.successful(Results.InternalServerError)
      }
    )

  private[this] def isUnderMaintenance(operation: Operation): Future[Boolean] = {
    val file = new File(s"${Config.FilePath.value}/${operation.name}.${Config.Extension.value}")
    Future(file.exists()).recoverWith {
      case e => Future.failed(e)
    }
  }
}
