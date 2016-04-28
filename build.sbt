name := "SupportPhoneBling"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  //groupID           %     artifactID        % revision
  "com.twilio.sdk"    %     "twilio-java-sdk" % "3.4.5",
  "io.spray"          %%    "spray-routing"   % "1.3.3",
  "io.spray"          %%    "spray-can"       % "1.3.3",
  "com.typesafe.akka" %%    "akka-actor"      % "2.4.4",
  "joda-time"         %     "joda-time"       % "2.9.3"
)