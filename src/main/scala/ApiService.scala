import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
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
      path("") {
        get {
          complete{
            <Response>
              <Dial>205-444-5555</Dial>
            </Response>
          }
        }
      }
    }
}
