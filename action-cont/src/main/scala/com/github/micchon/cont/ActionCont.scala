package com.github.micchon.cont

import play.api.mvc.{Action, AnyContent, Request, Result}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz._
import Scalaz._

object ActionCont extends IndexedContsTInstances with IndexedContsTFunctions {
  def apply[A](f: (A => Future[Result]) => Future[Result]): ActionCont[A] =
    ContT(f)

  def unit(implicit ec: ExecutionContext): ActionCont[Unit] =
    ActionCont.successful(())

  def fromFuture[A](future: => Future[A])(implicit ec: ExecutionContext): ActionCont[A] =
    ActionCont(future.flatMap(_))

  def successful[A](a: A)(implicit ec: ExecutionContext): ActionCont[A] =
    fromFuture(Future.successful(a))

  def failed[A](throwable: Throwable)(implicit ec: ExecutionContext): ActionCont[A] =
    fromFuture(Future.failed(throwable))

  def run(f: Request[AnyContent] => ActionCont[Result])(implicit ec: ExecutionContext): Action[AnyContent] =
    Action.async(f(_).run_)

  def recover[A](actionCont: ActionCont[A])(pf: PartialFunction[Throwable, Future[Result]])(implicit executor: ExecutionContext): ActionCont[A] =
    ActionCont(f => actionCont.run(f).recoverWith(pf))
}
