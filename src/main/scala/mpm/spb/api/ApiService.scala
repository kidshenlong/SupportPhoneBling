package mpm.spb.api

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.routing._
/**
  * Created by Michael on 27/04/2016.
  */
class ApiServiceActor extends Actor with ApiService{

  def actorRefFactory = context

  def receive = runRoute(route)

}

trait ApiService extends HttpService {

  val route =
    respondWithMediaType(`application/xml`) {
      pathPrefix("calls") {
        pathPrefix("forward") {
          pathEnd {
            post{
              complete{
                <Response>
                  <Dial callerId="+441279702157">205-444-5555</Dial>
                </Response>
              }
            }
          }
        }
      } ~ pathPrefix("sms") {
        pathPrefix("reply") {
          pathEnd {
            post{
              complete{

              }
            }
          }
        }
      }
    }
}
