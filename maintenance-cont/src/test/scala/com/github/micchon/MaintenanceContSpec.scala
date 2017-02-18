package com.github.micchon

import akka.util.Timeout
import com.github.micchon.maintenance.cont._
import com.github.micchon.maintenance.value.Operation
import org.scalatest._
import play.api.http.Status
import play.api.mvc.Results
import play.api.test.{FakeRequest, Helpers}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class MaintenanceContSpec extends FlatSpec {

  implicit val request = FakeRequest()
  implicit val duration: Timeout = 20.seconds

  "MaintenanceFlowCont" should "メンテナンス状態の場合は 503 ServiceUnavailable" in {
    val action = MaintenanceFlowCont.flow(Operation.Transaction) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(action) === Status.SERVICE_UNAVAILABLE)
  }

  it should "メンテナンス状態でない場合は 200 OK" in {
    val action = MaintenanceFlowCont.flow(Operation.Agency) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(action) === Status.OK)
  }
}
