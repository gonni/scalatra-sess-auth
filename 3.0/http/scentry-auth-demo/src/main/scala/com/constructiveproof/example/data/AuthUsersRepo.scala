package com.constructiveproof.example.data

import slick.jdbc.MySQLProfile.api.*
import slick.lifted.ProvenShape

import java.sql.Timestamp

object AuthUsersRepo {
  case class AuthUser(
                     userId: String,
                     userPw: String
                     )

  class AuthUserTableSchema(tag: Tag) extends Table[AuthUser](tag, None, "AUTH_USERS") {

    def userId = column[String]("USER_ID", O.PrimaryKey)
    def userPw = column[String]("USER_PW")

    override def * : ProvenShape[AuthUser] = (userId, userPw).mapTo[AuthUser]
  }

  val authUserQuery = TableQuery[AuthUserTableSchema]
}
