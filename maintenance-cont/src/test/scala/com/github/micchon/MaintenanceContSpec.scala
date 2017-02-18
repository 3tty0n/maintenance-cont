package com.github.micchon

import com.github.micchon.maintenance.cont._
import com.github.micchon.maintenance.value.Operation
import org.scalatest._
import play.api.http.Status
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers
import play.api.test.Helpers._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MaintenanceContSpec extends FlatSpec {

  trait SetUp {
    implicit val request = FakeRequest()
  }

  "MaintenanceCont" should "メンテナンス状態の場合は 503 ServiceUnavailable" in new SetUp {
    val cont = MaintenanceCont.flow(Operation.Transaction)(request) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(cont) === Status.SERVICE_UNAVAILABLE)
  }

  it should "メンテナンス状態でない場合は 200 Ok" in new SetUp {
    val cont = MaintenanceCont.flow(Operation.Agency)(request) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(cont) === Status.OK)
  }

  "MaintenanceFlowCont" should "メンテナンス状態の場合は 503 ServiceUnavailable" in new SetUp {
    val action = MaintenanceFlowCont.flow(Operation.Transaction) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(action) === Status.SERVICE_UNAVAILABLE)
  }

  it should "メンテナンス状態でない場合は 200 OK" in new SetUp {
    val action = MaintenanceFlowCont.flow(Operation.Agency) {
      _ => Future.successful(Results.Ok)
    }
    assert(Helpers.status(action) === Status.OK)
  }
}
