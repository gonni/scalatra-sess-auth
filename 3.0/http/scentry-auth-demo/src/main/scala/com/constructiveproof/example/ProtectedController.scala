package com.constructiveproof.example

import org.scalatra._
import com.constructiveproof.example.auth.AuthenticationSupport

import slick.jdbc.MySQLProfile.api._

class ProtectedController(val db: Database) extends ScalatraServlet with AuthenticationSupport {

  /**
   * Require that users be logged in before they can hit any of the routes in this controller.
   */
  before() {
    requireLogin()
  }

  get("/") {
    "This is a protected controller action. If you can see it, you're logged in."
  }
}


