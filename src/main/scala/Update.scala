import scala.io.StdIn.{readInt, readLine}
import java.sql.ResultSet


object Update {
  private val connection = dataBase.connection.getConnection

  def updatetoDB(): Unit = {
    val sql_statement = connection.createStatement()
    println("Please input applicant_id to update:")
    val update_id = readInt()
    val table = readLine("Please input the update table name (applicants/education/employment/language):")
    val column = readLine("Please input the column to update:")
    val new_value = readLine("Please input the new information:")

    update(update_id, table, column, new_value)
  }

  private def update(applicant_id: Int, table: String, column: String, new_value: Any): Unit = {
    val sql_statement = connection.createStatement()
    new_value match {
      case new_value: String => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = '${new_value}' WHERE applicant_id = ${applicant_id};")
      case new_value: Int => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = ${new_value} WHERE applicant_id = ${applicant_id};")
      case _ => println("Please input either a string or an int.")
    }
  }
}
