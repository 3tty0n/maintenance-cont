package com.github.micchon.maintenance

import play.api.mvc.{AnyContent, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

object MaintenanceFlowCont {

  def flow[A]
    (operation: Operation*)
    (f: MaintenanceRequest => Future[Result])
    (implicit request: Request[AnyContent], ec: ExecutionContext): Future[Result] = {
    (for {
      result <- MaintenanceCont(operation: _*)
    } yield result).run(f)
  }
}
