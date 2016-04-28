package mpm.spb.api


import akka.actor.Actor
import akka.actor.Status.Failure
import com.twilio.sdk.TwilioRestClient
import mpm.spb.domain._
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import scala.concurrent.{ExecutionContext, Future}
import akka.pattern.{ask, pipe}
import akka.actor.{ActorRef, _}
import scala.concurrent.ExecutionContext.Implicits.global
import mpm.spb.domain.Support._
import mpm.spb.MockSupport
import scala.collection.JavaConversions._


class TwilioService extends Actor {

  implicit val system = context.system

  val ACCOUNT_SID = ???

  val AUTH_TOKEN = ???

  val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)

  val ownNumber = "+441279702157"

  var callCount: Int = 0



  def receive: Receive = {
    case twilioRequest: CreateCall => pipe(handleCreateCall) to sender()
    case twilioRequest: ReplyText => pipe(handleReplyText(twilioRequest.body, twilioRequest.from)) to sender()
    case _ => sender ! Failure(new Exception())
  }

  def handleCreateCall:Future[String] = {
    getCurrentSupportDetails.map { supportMember =>
      callCount = callCount + 1
      s"""<Response><Dial callerId="$ownNumber">${supportMember.telephoneNumber}</Dial></Response>"""
    }
  }


  def handleReplyText(body: String, from: String): Future[Unit] = Future {
    if (body.contains("who's on support?")) {
      getCurrentSupportDetails.map { supportMember =>
        val newMessage = s"""Hello, ${supportMember.firstName} ${supportMember.lastName} is currently on support"""
        sendTextMessage(from, newMessage)
      }
    } else if (body.contains("has there been any support calls?")) {
      getCurrentSupportDetails.map { supportMember =>
        getCallCount.map { count =>
          val newMessage = s"""Hello,""" + (if (count > 0) s"yes! There has been $count calls" else "no. None at all :(")
          sendTextMessage(from, newMessage)
        }
      }
    } else {
      sendTextMessage(from, "Sorry I didn't quite understand that")
    }
  }

  def sendTextMessage(to: String, body: String) = Future {

    val mainAccount = client.getAccount

    val messageFactory = mainAccount.getMessageFactory

    val params:List[NameValuePair] = List(
      new BasicNameValuePair("To", to),
      new BasicNameValuePair("From", ownNumber),
      new BasicNameValuePair("Body", body)
    )

    messageFactory.create(params)
  }


  def getCurrentSupportDetails:Future[SupportMember] = Future {
    MockSupport.team.head
  }

  def getCallCount:Future[Int] = Future {
    callCount
  }


}
