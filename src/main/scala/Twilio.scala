/**
 * Created by pattem92 on 27/04/2016.
 */

import com.twilio.sdk.TwilioRestClient
import com.twilio.sdk.resource.instance.Account
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

//import scala.collection.JavaConverters
import scala.collection.JavaConversions._

object Twilio {

  val ACCOUNT_SID = ???

  val AUTH_TOKEN = ???

  def main2(args: Array[String]) = {



    val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)

    // Get the main account (The one we used to authenticate the client)
    val mainAccount = client.getAccount

    val messageFactory = mainAccount.getMessageFactory

    val params:List[NameValuePair] = List(
      new BasicNameValuePair("To", "XXXXX"),
      new BasicNameValuePair("From", "XXXX"),
      new BasicNameValuePair("Body", "XXXXX")
    )

    val sms = messageFactory.create(params)

  }

}
