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

  val ownNumber = ???


  def receive: Receive = {
    case twilioRequest: CreateCall => pipe(handleCreateCall) to sender()
    case twilioRequest: ReplyText => pipe(handleReplyText(twilioRequest.body, twilioRequest.from)) to sender()
    case _ => sender ! Failure(new Exception())
  }

  def handleCreateCall:Future[String] = {
    getCurrentSupportDetails.map { supportMember =>
      s"""<Response><Dial callerId="$ownNumber">${supportMember.telephoneNumber}</Dial></Response>"""
    }
  }

  def handleReplyText(body: String, from: String): Future[Unit] = Future {
    if (body.contains("who's on support?")) {
      getCurrentSupportDetails.map { supportMember =>
        val newMessage = s"""Hello, ${supportMember.firstName} ${supportMember.lastName} is currently on support"""
        sendTextMessage(from, newMessage)
      }
    }
  }

  def sendTextMessage(to: String, body: String) = Future {


    // Get the main account (The one we used to authenticate the client)
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



}
