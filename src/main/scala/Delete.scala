
import java.sql.ResultSet
import scala.io.StdIn.{readInt, readLine}

object Delete {

  private val connection = dataBase.connection.getConnection

  def deletetoDB(): Unit = {
    val sql_statement = connection.createStatement()
    println("Please input the applicant id to delete: ")
    val delete_id = readInt()
  
    delete(delete_id)
  }

  private def delete(applicant_id: Int): Unit = {
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"DELETE FROM education WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM employment WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM language WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM applicants WHERE applicant_id = ${applicant_id};")
  }
}
