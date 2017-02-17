package maintenance

import cont.ActionCont
import play.api.mvc.{Request, Result, Results}
import scala.concurrent.Future
import scala.util.control.NonFatal
import scala.concurrent.ExecutionContext.Implicits.global

object MaintenanceFlowCont {

  def flow[A](operation: Operation*)(implicit request: Request[A]): Future[Result] =
    (for {
      _ <- ActionCont.recover(MaintenanceCont(operation: _*)) {
        case NonFatal(_) => Future.successful(Results.ServiceUnavailable)
      }
    } yield { ActionCont.successful(()) }).run(_ => Future.successful(Results.Ok))
}
