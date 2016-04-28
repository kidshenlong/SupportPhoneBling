package mpm.spb.api

import akka.actor.{ActorRefFactory, Props, Actor}
import mpm.spb.domain.{ReplyText, CreateCall}
import mpm.spb.domain.Twilio._
import mpm.spb.util.RouteHandlers
import spray.http.FormData
import spray.http.MediaTypes._
import spray.routing._
import spray.httpx.encoding._
import spray.httpx.unmarshalling._
import spray.httpx.unmarshalling.FromRequestUnmarshaller
import spray.httpx.marshalling._
import spray.httpx

/**
  * Created by Michael on 27/04/2016.
  */
class ApiServiceActor extends Actor with ApiService {

  implicit val system = context.system

  def actorRefFactory = context

  val twilioService = context.actorOf(Props(new TwilioService()), "twilio-service")


  def receive = runRoute(route)



}



trait ApiService extends HttpService with RouteHandlers {



  val route =
    respondWithMediaType(`application/xml`) {
      pathPrefix("calls") {
        pathPrefix("forward") {
          pathEnd {
            post {
              handleComplete(CreateCall())
            }
          }
        }
      } ~ pathPrefix("sms") {
        pathPrefix("reply") {
          pathEnd {
            post {
              entity(as[FormData]) { txt =>
              //entity(as[TextMessage]) { txt =>
                //println(txt.fields.toMap.get("Body"))
                val text = TextMessage(txt.fields.toMap.getOrElse("Body","").toLowerCase, txt.fields.toMap.getOrElse("From", ""))
                handleComplete(ReplyText(text.body, text.from))
              }
            }
          }
        }
      }
    }
}
