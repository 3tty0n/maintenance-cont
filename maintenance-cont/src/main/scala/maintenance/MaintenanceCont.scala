package maintenance

import java.io.File
import cont.ActionCont
import play.api.mvc.Request
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MaintenanceCont {

  val MAINTENANCE_FILE_PATH = "resources/maintenance/operation"
  val EXTENSION = "maintenance"

  def apply[A](operation: Operation*)(implicit request: Request[A]): ActionCont[Seq[Unit]] =
    ActionCont.fromFuture(Future.sequence(operation.map(isUnderMaintenance(_).flatMap {
      case true => Future.failed(new MaintenanceException)
      case false => Future.successful()
    })))

  private def isUnderMaintenance(operation: Operation): Future[Boolean] = {
    val file = new File(s"$MAINTENANCE_FILE_PATH/${operation.name}.$EXTENSION")
    Future(file.exists())
  }
}
