package tests

import java.sql.{Connection,DriverManager}

object ScalaJdbcConnectSelect extends App {
  val url = "jdbc:mysql://localhost:3306/applicants"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "root"
  var connection:Connection = _
  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement
    val rs = statement.executeQuery("SELECT applicant_id, name FROM applicants")
    while (rs.next) {
      val host = rs.getString("applicant_id")
      val user = rs.getString("name")
      println("applicant_id = %s, name = %s".format(host,user))
    }
  } catch {
    case e: Exception => e.printStackTrace
  }
  connection.close
}
