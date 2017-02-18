package com.github.micchon.app.cont

import java.io.File

import com.github.micchon.cont.ActionCont
import com.github.micchon.app.value.Operation
import com.github.micchon.app.value.{MaintenanceConfig => Config}
import play.api.mvc.{Request, Result, Results}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class MaintenanceCont {

  def flow[A](operation: Operation*)(implicit request: Request[A]): ActionCont[Request[A]] =
    ActionCont((f: Request[A] => Future[Result]) =>
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

object MaintenanceCont extends MaintenanceCont