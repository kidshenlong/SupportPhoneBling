package mpm.spb.domain

/**
 * Created by pattem92 on 28/04/2016.
 */
object Twilio {
  //ToCountry=GB&ToState=Bishops+Stortford&SmsMessageSid=SMf8b1e36ad4980f9472eafb0c5135934b&NumMedia=0&ToCity=&FromZip=&SmsSid=SMf8b1e36ad4980f9472eafb0c5135934b&FromState=&SmsStatus=received&FromCity=&Body=Who%27s+on+support%3F&FromCountry=GB&To=%2B441279702157&ToZip=&NumSegments=1&MessageSid=SMf8b1e36ad4980f9472eafb0c5135934b&AccountSid=AC18a7261e79cee5ca1dd1baca37cdf256&From=%2B447903225562&ApiVersion=2010-04-01


  case class TextMessage(
                          body: String,
                          from: String
                          )

}
