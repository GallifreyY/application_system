import java.sql.ResultSet
import scala.io.StdIn.{readInt, readLine}


object View {
  private val connection = dataBase.connection.getConnection

  def viewtoDB(): Unit = {
    val sql_statement = connection.createStatement()
    println("Please input the applicant id to view: ")
    val view_id = readInt()
    val table = readLine("Please input the table to view (applicants/education/employment/language):")
    view(view_id,table)
  }
  
    private def view(applicant_id: Int,table:String): Unit = {
      val sql_statement = connection.createStatement()
      sql_statement.executeQuery(s"SELECT * from applicants WHERE applicant_id = ${applicant_id};")
      println(s"'${table}' info of applicant_id: ${applicant_id} is: ")
      import java.sql.ResultSet
      val res: ResultSet = sql_statement.executeQuery(s"SELECT * from applicants WHERE applicant_id = ${applicant_id};")
      val resmd = res.getMetaData
      val colNum = resmd.getColumnCount
      while (res.next) {
        for (i <- 1 to colNum) {
          if (i > 1) {
            val colVal = res.getString(i)
            println(resmd.getColumnName(i) + ": " + colVal + ",")
          }
        }

      }
    }
}


