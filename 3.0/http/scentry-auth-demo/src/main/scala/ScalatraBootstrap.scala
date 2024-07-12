import com.constructiveproof.example._
import org.scalatra._
import jakarta.servlet.ServletContext
import slick.jdbc.MySQLProfile.api._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) = {

    val db = Database.forURL(
      url = "jdbc:mysql://horusa:3306/sample?useSSL=false",
      user = "root",
      password = "root",
      driver = "com.mysql.cj.jdbc.Driver"
    )

    context.mount(new ProtectedController(db), "/*")
    context.mount(new SessionsController(db), "/sessions/*")
  }
}
