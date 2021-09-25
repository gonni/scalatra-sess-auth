organization := "com.example"
name := "Scalatra Squeryl"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.6"

val ScalatraVersion = "2.8.2"

libraryDependencies ++= Seq(
  "org.scalatra"            %% "scalatra"           % ScalatraVersion,
  "org.scalatra"            %% "scalatra-scalatest" % ScalatraVersion    % Test,
  "org.squeryl"             %% "squeryl"            % "0.9.15",
  "com.h2database"          %  "h2"                 % "1.4.200",
  "com.mchange"             %  "c3p0"               % "0.9.5.5",
  "ch.qos.logback"          % "logback-classic"     % "1.2.3"            % Provided,
  "org.eclipse.jetty"       %  "jetty-webapp"       % "9.4.35.v20201120" % Provided,
  "javax.servlet"           %  "javax.servlet-api"  % "3.1.0"            % Provided
)

ThisBuild / evictionErrorLevel := Level.Warn

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)
