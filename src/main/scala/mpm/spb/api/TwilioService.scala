package mpm.spb.api


import akka.actor.Actor
import akka.actor.Status.Failure
import mpm.spb.domain._
import scala.concurrent.{ExecutionContext, Future}
import akka.pattern.{ask, pipe}
import akka.actor.{ActorRef, _}
import scala.concurrent.ExecutionContext.Implicits.global

class TwilioService extends Actor {

  implicit val system = context.system

  def receive: Receive = {
    case twilioRequest: CreateCall => {
      //handleCreateCall pipeTo sender
      pipe(handleCreateCall) to sender()
    }
    //case twilioRequest: CreateCall =>
    case _ => sender ! Failure(new Exception())
  }

  def handleCreateCall:Future[Any] = ???

}
