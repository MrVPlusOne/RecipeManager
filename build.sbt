name := "RecipeManager"

version := "0.1"

organization in ThisBuild := "mrvplusone.github.io"
scalaVersion in ThisBuild := "2.12.7"

libraryDependencies ++= Seq(
  //  "com.lihaoyi" %% "fastparse" % "2.0.4",
  "org.scalacheck" %% "scalacheck" % "1.14.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "com.lihaoyi" %% "ammonite-ops" % "1.0.3",


  //  "com.typesafe.akka" %% "akka-actor" % "2.5.12",
  //  "com.typesafe.akka" %% "akka-testkit" % "2.5.12" % Test,
)

//mainClass in assembly := Some(???)
//assemblyJarName in assembly := ???