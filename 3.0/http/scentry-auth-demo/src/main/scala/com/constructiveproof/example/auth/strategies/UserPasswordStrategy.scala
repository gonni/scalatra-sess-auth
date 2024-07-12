package com.constructiveproof.example.auth.strategies

import org.scalatra.{Cookie, CookieOptions, ScalatraBase}
import org.scalatra.auth.ScentryStrategy
import com.constructiveproof.example.models.User
import jakarta.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.slf4j.{Logger, LoggerFactory}
import slick.jdbc.MySQLProfile.api.*

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class UserPasswordStrategy(protected val app: ScalatraBase, val db: Database)
                          (implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  val COOKIE_KEY = "rememberMe"
  private val oneWeek: Int =  24 * 3600

  override def name: String = "UserPassword"

  private def login: String = app.params.getOrElse("login", "")
  private def password: String = app.params.getOrElse("password", "")


  /***
    * Determine whether the strategy should be run for the current request.
    */
  override def isValid(implicit request: HttpServletRequest): Boolean = {
    logger.info("UserPasswordStrategy: determining isValid: " + (login != "" && password != "").toString)
    login != "" && password != ""
  }

  /**
   *  In real life, this is where we'd consult our data store, asking it whether the user credentials matched
   *  any existing user. Here, we'll just check for a known login/password combination and return a user if
   *  it's found.
   */
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    println("Auth1: loging with userID, userPW")
    logger.info("UserPasswordStrategy: attempting authentication")

    if(login == "foo" && password == "bar") {
      logger.info("UserPasswordStrategy: login succeeded")
      Some(User("foo"))
    } else {
      logger.info("UserPasswordStrategy: login failed")
      None
    }
  }

  def uuid(): String = java.util.UUID.randomUUID().toString

  /**
   * What should happen if the user is currently not authenticated?
   */
  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    app.redirect("/sessions/new")
  }

}

