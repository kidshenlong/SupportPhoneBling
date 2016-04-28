package mpm.spb.util

import java.util.concurrent.TimeUnit

import akka.actor.ActorRef
import akka.util.Timeout
import mpm.spb.domain.TwilioRequest
import spray.http.StatusCodes._
import spray.routing.HttpService
import akka.pattern._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

/**
 * Created by pattem92 on 28/04/2016.
 */
trait RouteHandlers extends HttpService {

  val twilioService: ActorRef

  val gatewayTimeout: Timeout = Timeout(5000, TimeUnit.MILLISECONDS)

  /** sends activity request to playlister service actor and completes the route when the returned future is completed */
  protected def handleComplete(twilioRequest: TwilioRequest) = {
    onComplete((twilioService ? twilioRequest) (gatewayTimeout)){
      case Success(response: String) => complete(OK, response)
      case _ => complete(OK, "")
    }
  }



}
