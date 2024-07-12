organization := "com.constructiveproof.example"
name := "ScentryAuthDemo"
version := "0.1.0-SNAPSHOT"
scalaVersion := "3.3.1"

val ScalatraVersion = "3.0.0"

libraryDependencies ++= Seq(
  "org.scalatra"            %% "scalatra-auth-jakarta"   % ScalatraVersion,
  "org.scalatra"            %% "scalatra-jakarta"        % ScalatraVersion,
  "org.scalatra"            %% "scalatra-specs2-jakarta" % ScalatraVersion  % Test,
  "ch.qos.logback"          % "logback-classic"          % "1.4.11"         % Provided,
  "org.eclipse.jetty"       %  "jetty-webapp"            % "11.0.18"        % Provided,
  "jakarta.servlet"         %  "jakarta.servlet-api"     % "5.0.0"          % Provided,
  "com.typesafe.slick" %% "slick" % "3.5.1",
  "com.h2database" % "h2"                  % "2.2.224",
  "mysql" % "mysql-connector-java" % "8.0.27",
)

enablePlugins(JettyPlugin)

Jetty / containerLibs := Seq("org.eclipse.jetty" % "jetty-runner" % "11.0.18" intransitive())
