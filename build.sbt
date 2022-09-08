name := "SensorStats"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"

//resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-effect" % "2.1.4",
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "3.0.4",
  "com.github.tototoshi" %% "scala-csv" % "1.3.6",
"com.typesafe.akka" %% "akka-stream" % AkkaVersion,
"org.scalatest" %% "scalatest" % "3.2.0" % Test,
"org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"
)

scalacOptions += "-deprecation"