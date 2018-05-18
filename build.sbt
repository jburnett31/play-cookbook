name := """play-cookbook"""
organization := "com.jburnett"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

resolvers ++= Seq(
	"Sonatype OSS" at "https://oss.sonatype.org/content/groups/public"
)

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	specs2 % Test,
	"com.typesafe.slick" %% "slick" % "3.2.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
	"org.postgresql" % "postgresql" % "9.4.1209"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.jburnett.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.jburnett.binders._"
