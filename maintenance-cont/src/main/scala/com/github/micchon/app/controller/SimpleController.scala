package com.github.micchon.app.controller

import com.github.micchon.app.cont.MaintenanceCont
import com.github.micchon.app.value.Operation
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Results
import scala.concurrent.Future

class SimpleController extends Controller {

  def index = Action.async { implicit request =>
    MaintenanceCont.flow(Operation.Transaction)(request) {
      _ => Future.successful(Results.Ok)
    }
  }

}

object SimpleController extends SimpleController
