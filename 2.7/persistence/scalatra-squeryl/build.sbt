organization := "com.example"
name := "Scalatra Squeryl"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.0"

val ScalatraVersion = "2.7.+"

libraryDependencies ++= Seq(
  "org.scalatra"            %% "scalatra"           % ScalatraVersion,
  "org.scalatra"            %% "scalatra-scalate"   % ScalatraVersion,
  "org.scalatra"            %% "scalatra-scalatest" % ScalatraVersion    % Test,
  "org.squeryl"             %% "squeryl"            % "0.9.14",
  "com.h2database"          %  "h2"                 % "1.4.199",
  "com.mchange"             %  "c3p0"               % "0.9.5.4",
  "ch.qos.logback"          % "logback-classic"     % "1.2.3"            % Provided,
  "org.eclipse.jetty"       %  "jetty-webapp"       % "9.4.19.v20190610" % Provided,
  "javax.servlet"           %  "javax.servlet-api"  % "3.1.0"            % Provided
)

enablePlugins(ScalatraPlugin)