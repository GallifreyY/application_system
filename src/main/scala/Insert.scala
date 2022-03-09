import java.sql.ResultSet
import scala.io.StdIn.{readInt, readLine}

object Insert {
    private val connection = dataBase.connection.getConnection

    def insertoDB(): Unit = {
        val sql_statement = connection.createStatement()
        println("Please enter your personal information below:\n")
        val applicant_id = readLine("Please input your application id: ")
        val name = readLine("Please input your name: ")
        val gender = readLine("Please input your gender: ")
        val birthday = readLine("Please input your birthday (yyyy-mm-dd):")
        val email = readLine("Please input your email:")
        println("Please enter university rank (int):")
        val university_rank = readInt()
        val location = readLine("Please input your location:")
        val qualification = readLine("Please input your qualification:")
        val major = readLine("Please input your major:")
        val start_date = readLine("Please input the start date (yyyy-mm-dd):")
        val end_date = readLine("Please input the end date (yyyy-mm-dd):")
        println("Please input your best score (int):")
        val best_score = readInt()
        println("Please input your gpa (hundred mark system, int):")
        val gpa = readInt()
        println("Please input your ranking (int):")
        val ranking = readInt()
        println("Please input your sponsorship (int):")
        val sponsorship = readInt()
        val occupation = readLine("Please input your occupation:")
        val employed_from = readLine("Please input the start date of the work experience(yyyy-mm-dd):")
        val employed_to = readLine("Please input the end date of the work experience(yyyy-mm-dd):")
        val company_name = readLine("Please input the company name:")
        val exam_type = readLine("Please input the exam type(TOEFL or IELTS):")
        val exam_date = readLine("Please input the exam date(yyyy-mm-dd):")
        println("Please input your score (int):")
        val score = readInt()
        println("Please input your listening score (int):")
        val listening = readInt()
        println("Please input your reading score (int):")
        val reading = readInt()
        println("Please input your speaking score (int):")
        val speaking = readInt()
        println("Please input your writing score (int):")
        val writing = readInt()


        insert(applicant_id, name,gender, birthday,email,
            university_rank, location, qualification, major,
            start_date, end_date, best_score, gpa, ranking, sponsorship,
            occupation, employed_from, employed_to, company_name,
            exam_type,exam_date, score, listening, reading, speaking, writing)
    }

    private def insert(applicant_id:String, name:String, gender: String, birthday: String, email: String,
                     university_rank:Int, location:String, qualification:String, major:String,
                     start_date:String, end_date:String, best_score: Int, gpa: Int, ranking: Int, sponsorship: Int,
                     occupation:String, employed_from:String, employed_to: String, company_name:String,
                     exam_type: String,exam_date: String, score: Int, listening: Int, reading: Int, speaking: Int, writing: Int): Unit = {
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"INSERT INTO applicants VALUES ('${applicant_id}','${name}', '${gender}', STR_TO_DATE('${birthday}','%Y-%m-%d'), '${email}');")
    sql_statement.executeUpdate(s"INSERT INTO education VALUES ('${applicant_id}','${university_rank}', '${location}', '${qualification}', '${major}'," +
    s"STR_TO_DATE('${start_date}','%Y-%m-%d'), STR_TO_DATE('${end_date}','%Y-%m-%d'),${best_score},${gpa}, ${ranking},${sponsorship});")
    sql_statement.executeUpdate(s"INSERT INTO employment VALUES ('${applicant_id}', '${occupation}', STR_TO_DATE('${employed_from}','%Y-%m-%d'), STR_TO_DATE('${employed_to}','%Y-%m-%d'),'${company_name}');")
    sql_statement.executeUpdate(s"INSERT INTO language VALUES ('${applicant_id}','${exam_type}', STR_TO_DATE('${exam_date}','%Y-%m-%d'), ${score},${listening}, ${reading},${speaking},${writing});")
  }
}