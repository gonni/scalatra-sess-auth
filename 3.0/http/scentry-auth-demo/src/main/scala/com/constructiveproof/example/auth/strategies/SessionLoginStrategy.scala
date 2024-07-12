package com.constructiveproof.example.auth.strategies

import com.constructiveproof.example.models.User
import jakarta.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.scalatra.{Cookie, CookieOptions, ScalatraBase}
import org.scalatra.auth.ScentryStrategy
import org.slf4j.{Logger, LoggerFactory}

import slick.jdbc.MySQLProfile.api.*

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SessionLoginStrategy (protected val app: ScalatraBase, val db: Database)
                           (implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  val COOKIE_KEY = "rememberMe"
  private val oneWeek: Int =  24 * 3600

  override def name: String = "UserPassword"

  private def login: String = app.params.getOrElse("login", "")
  private def password: String = app.params.getOrElse("password", "")

  private def tokenVal: String = {
    app.cookies.get(COOKIE_KEY) match {
      case Some(token) =>
        println("current session token => " + token)
        token
      case None => ""
    }
  }

//  /***
//   * Determine whether the strategy should be run for the current request.
//   */
//  override def isValid(implicit request: HttpServletRequest): Boolean = {
//    logger.info("UserPasswordStrategy: determining isValid: " + (login != "" && password != "").toString)
//    login != "" && password != ""
//  }

  override def beforeAuthenticate(implicit request: HttpServletRequest, response: HttpServletResponse): Unit = {
    println("auth")
  }
  /**
   *  In real life, this is where we'd consult our data store, asking it whether the user credentials matched
   *  any existing user. Here, we'll just check for a known login/password combination and return a user if
   *  it's found.
   */
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    println("Auth1: loging with userID, userPW")
    logger.info("UserPasswordStrategy: attempting authentication")
    import com.constructiveproof.example.data.AuthSessionRepo._

    if(login == "foo" && password == "bar") {
      logger.info("UserPasswordStrategy: login succeeded")
      // --
      val token = uuid()

      val insertResult = Await.result(db.run(insertNewSession(token, "foo")), Duration.Inf)
      println("Insert Result :" + insertResult)
      app.cookies.set(COOKIE_KEY, token)(CookieOptions(maxAge = oneWeek, path = "/"))
      // --
      Some(User("foo"))
    } else {
      logger.info("UserPasswordStrategy: login failed")

//      println("AuthMix => use session key from DB")
       val qry = authSessionQuery.filter(_.sessKey === tokenVal).map(_.userId)

      val result = Await.result(db.run(qry.result.headOption), Duration.Inf)
//      println("Session From DB :" + result)
      result match {
        case Some(userId) =>
          println(s"detected session, alreay logged-in : $userId with $tokenVal")
          Some(User(userId))
        case _ => None
      }
    }
  }

  def uuid(): String = java.util.UUID.randomUUID().toString

  /**
   * What should happen if the user is currently not authenticated?
   */
  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    println("unAuthenticated ..")
    app.redirect("/sessions/new")
  }

  override def beforeLogout(user: User)(implicit request: HttpServletRequest, response: HttpServletResponse): Unit = {
    //
    import com.constructiveproof.example.data.AuthSessionRepo._
    val resDel = Await.result(db.run(deleteSessionKey(tokenVal)), Duration.Inf)
    println("deleted token in db => " + tokenVal)

    //
    logger.info("rememberMe: beforeLogout")
    if (user != null) {
      user.forgetMe()
    }

    app.cookies.delete(COOKIE_KEY)(CookieOptions(path = "/"))

  }

}