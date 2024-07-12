package com.constructiveproof.example.auth

import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import com.constructiveproof.example.models.User
import org.scalatra.ScalatraBase
import org.slf4j.{Logger, LoggerFactory}
import com.constructiveproof.example.auth.strategies.{RememberMeStrategy, SessionLoginStrategy, UserPasswordStrategy}
import slick.jdbc.MySQLProfile.api.*

trait AuthenticationSupport extends ScalatraBase with ScentrySupport[User] {
  self: ScalatraBase =>

  def db: Database
  protected def fromSession: PartialFunction[String, User] = { case id: String => User(id)  }
  protected def toSession: PartialFunction[User, String] = { case usr: User => usr.id }

  protected val scentryConfig: ScentryConfiguration = new ScentryConfig {
    override val login = "/sessions/new"
  }.asInstanceOf[ScentryConfiguration]

  val logger: Logger = LoggerFactory.getLogger(getClass)

  protected def requireLogin(): Unit = {
    println("Check Session Login .. ")
    if (!isAuthenticated) {
//      redirect(scentryConfig.login)
      scentry.strategies("MixedAuth").authenticate() match
        case None => redirect(scentryConfig.login)
        case Some(user) =>
          println("Already authenticated with session user : " + user)
          scentry.authenticate()
    }

  }

  /**
   * If an unauthenticated user attempts to access a route which is protected by Scentry,
   * run the unauthenticated() method on the UserPasswordStrategy.
   */
  override protected def configureScentry(): Unit = {
    scentry.unauthenticated {
//      scentry.strategies("UserPassword").unauthenticated()
      scentry.strategies("MixedAuth").unauthenticated()
    }
  }

  /**
   * Register auth strategies with Scentry. Any controller with this trait mixed in will attempt to
   * progressively use all registered strategies to log the user in, falling back if necessary.
   */
  override protected def registerAuthStrategies(): Unit = {
//    scentry.register("UserPassword", app => new UserPasswordStrategy(app, db))
//    scentry.register("RememberMe", app => new RememberMeStrategy(app, db))
    scentry.register("MixedAuth", app => new SessionLoginStrategy(app, db))
  }

}
