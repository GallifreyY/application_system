import java.sql.ResultSet
import scala.io.StdIn.{readChar, readLine}

object Insert {
    private val connection = dataBase.connection.getConnection

    def insertoDB(): Unit = {
        val sql_statement = connection.createStatement()
        println("Please enter your personal information below:\n")
        val name = readLine("Please input your name: ")
        val gender = readLine("Please input your gender: ")
        //add more 
        //insert(name, gender, xxx)
    }

    private def insert(applicant_id: Int, name:String, gender: String, birthday: String, email: String,
                     university_name:String, location:String, qualification:String, major:String,
                     start_date:String, end_date:String, best_score: Int, gpa: Int, ranking: Int, sponsorship: Int,
                     occupation:String, employed_from:String, employed_to: String, company_name:String,
                     exam_type: String,exam_date: String, score: Int, listening: Int, reading: Int, speaking: Int, writing: Int): Unit = {
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"INSERT INTO applicants VALUES (${applicant_id}, '${name}', '${gender}', STR_TO_DATE('${birthday}','%Y-%m-%d'), '${email}');")
    sql_statement.executeUpdate(s"INSERT INTO education VALUES (${applicant_id}, '${university_name}', '${location}', '${qualification}', '${major}'," +
    s"STR_TO_DATE('${start_date}','%Y-%m-%d'), STR_TO_DATE('${end_date}','%Y-%m-%d'),${best_score},${gpa}, ${ranking},${sponsorship});")
    sql_statement.executeUpdate(s"INSERT INTO employment VALUES (${applicant_id}, '${occupation}', STR_TO_DATE('${employed_from}','%Y-%m-%d'), STR_TO_DATE('${employed_to}','%Y-%m-%d'),'${company_name}');")
    sql_statement.executeUpdate(s"INSERT INTO language VALUES (${applicant_id}, '${exam_type}', STR_TO_DATE('${exam_date}','%Y-%m-%d'), ${score},${listening}, ${reading},${speaking},${writing});")
  }
}