import org.apache.commons.dbcp2.*

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.Properties

object dataBase {
  val db = "jdbc:mysql://localhost/applicants"
  val connection = new BasicDataSource()
  connection.setUsername("root")
  connection.setPassword("yyc19980708")
  connection.setDriverClassName("com.mysql.cj.jdbc.Driver")
  connection.setUrl(db)
}
