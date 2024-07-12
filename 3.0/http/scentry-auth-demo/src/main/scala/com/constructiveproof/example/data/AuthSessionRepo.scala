package com.constructiveproof.example.data

import slick.jdbc.MySQLProfile.api.*
import slick.lifted.ProvenShape

import java.sql.Timestamp

object AuthSessionRepo {
  case class AuthSession(
                        sessKey: String,
                        userId: String,
                        validDt: Option[Timestamp]
                        )

  class AuthSessionTableSchema(tag: Tag) extends Table[AuthSession](tag, None, "AUTH_SESSIONS") {

    def sessKey = column[String]("SESS_KEY", O.PrimaryKey)
    def userId = column[String]("USER_ID")
    def validDt = column[Option[Timestamp]]("VALID_DT")

    override def * : ProvenShape[AuthSession] = (sessKey, userId, validDt).mapTo[AuthSession]
  }

  val authSessionQuery = TableQuery[AuthSessionTableSchema]

  def insertNewSession(sessKey: String, userId: String) =
    authSessionQuery += AuthSession(sessKey, userId, None)

  def deleteSessionKey(sessKey: String) =
    authSessionQuery.filter(_.sessKey === sessKey).delete
  

}
