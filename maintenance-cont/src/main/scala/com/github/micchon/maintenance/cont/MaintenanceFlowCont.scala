package com.github.micchon.maintenance.cont

import com.github.micchon.cont.ActionCont
import com.github.micchon.maintenance.request.MaintenanceRequest
import com.github.micchon.maintenance.value.Operation
import play.api.mvc.{AnyContent, Request, Result, Results}

import scala.concurrent.{ExecutionContext, Future}

object MaintenanceFlowCont {

  def flow[A]
  (operation: Operation*)
    (f: Seq[MaintenanceRequest] => Future[Result])
    (implicit request: Request[AnyContent], ec: ExecutionContext): Future[Result] = {
    (for {
      result <- ActionCont.recover(MaintenanceCont(operation: _*)) {
        case _ => Future.successful(Results.ServiceUnavailable)
      }
    } yield result).run(f)
  }

}
