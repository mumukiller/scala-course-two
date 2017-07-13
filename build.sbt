name := "advanced_scala_course"

version := "1.0"

lazy val `advanced_scala_course` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  "org.scalikejdbc" %% "scalikejdbc"       % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "2.5.1",
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.h2database" % "h2" % "1.4.193",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  specs2 % Test
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"