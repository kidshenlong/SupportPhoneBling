package mpm.spb.domain

/**
 * Created by pattem92 on 28/04/2016.
 */
sealed abstract class TwilioRequest

case class CreateCall() extends TwilioRequest
case class CreateText() extends TwilioRequest

