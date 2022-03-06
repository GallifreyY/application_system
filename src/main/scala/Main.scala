import java.awt.Choice
import java.sql.ResultSet
import cats.effect.*
import cats.syntax.all.*

import java.util.Date
import io.StdIn.*

object Main extends IOApp {
  private val connection = dataBase.connection.getConnection

  //database ini
  private def iniDB(): Unit = {
    println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS applicants(applicant_id CHAR(9), " +
    "name VARCHAR(64) NOT NULL, gender VARCHAR(64), birthday DATE, " +
    "email VARCHAR(64), PRIMARY KEY(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS education(applicant_id CHAR(9) NOT NULL, " +
    "university_name VARCHAR(64) NOT NULL, location VARCHAR(64), qualification VARCHAR(64), " +
    "major VARCHAR(64), start_date DATE, end_date DATE, best_score INT, gpa DECIMAL(10, 0), " +
    "ranking INT, sponsorship BOOL, PRIMARY KEY(applicant_id), FOREIGN KEY (applicant_id) "+
    "REFERENCES applicants(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS employment(applicant_id CHAR(9) NOT NULL, "+
    "occupation VARCHAR(64), employed_from DATE, employed_to DATE, company_name VARCHAR(64) NOT NULL, "+
    "PRIMARY KEY(applicant_id), FOREIGN KEY(applicant_id) REFERENCES applicants(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS language(applicant_id CHAR(9) NOT NULL, exam_type VARCHAR(64), "+
    "exam_date DATE, score INT, listening INT, reading INT, speaking INT, writing INT, PRIMARY KEY(applicant_id), "+
    "FOREIGN KEY (applicant_id) REFERENCES applicants(applicant_id));")
    }

  private def insert(applicant_id: Int, name:String, gender: String, birthday: String, email: String,
                     university_name:String, location:String, qualification:String, major:String,
                     start_date:String, end_date:String, best_score: Int, gpa: Int, ranking: Int, sponsorship: Int,
                     occupation:String, employed_from:String, employed_to: String, company_name:String,
                     exam_type: String,exam_date: String, score: Int, listening: Int, reading: Int, speaking: Int, writing: Int): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"INSERT INTO applicants VALUES (${applicant_id}, '${name}', '${gender}', STR_TO_DATE('${birthday}','%Y-%m-%d'), '${email}');")
    sql_statement.executeUpdate(s"INSERT INTO education VALUES (${applicant_id}, '${university_name}', '${location}', '${qualification}', '${major}'," +
    s"STR_TO_DATE('${start_date}','%Y-%m-%d'), STR_TO_DATE('${end_date}','%Y-%m-%d'),${best_score},${gpa}, ${ranking},${sponsorship});")
    sql_statement.executeUpdate(s"INSERT INTO employment VALUES (${applicant_id}, '${occupation}', STR_TO_DATE('${employed_from}','%Y-%m-%d'), STR_TO_DATE('${employed_to}','%Y-%m-%d'),'${company_name}');")
    sql_statement.executeUpdate(s"INSERT INTO language VALUES (${applicant_id}, '${exam_type}', STR_TO_DATE('${exam_date}','%Y-%m-%d'), ${score},${listening}, ${reading},${speaking},${writing});")
  }
//STR_TO_DATE("${birthday}","%Y-%m-%d")
  private def delete(applicant_id: Int): Unit = {
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"DELETE FROM education WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM employment WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM language WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM applicants WHERE applicant_id = ${applicant_id};")
  }

  private def update(applicant_id: Int,table: String, column: String, new_value: Any): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    new_value match {
      case new_value: String => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = '${new_value}' WHERE applicant_id = ${applicant_id};")
      case new_value: Int => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = ${new_value} WHERE applicant_id = ${applicant_id};")
      case _ => println("Please input either a string or an int.")
    }
  }


  private def view(applicant_id: Int,table:String): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeQuery(s"SELECT * from applicants WHERE applicant_id = ${applicant_id};")
    println(s"'${table}' info of applicant_id: ${applicant_id} is: ")
    import java.sql.ResultSet
    val res:ResultSet = sql_statement.executeQuery(s"SELECT * from applicants WHERE applicant_id = ${applicant_id};")
    val resmd = res.getMetaData
    val colNum = resmd.getColumnCount
    while (res.next) {
      for (i <- 1 to colNum) {
        if (i > 1) {
          val colVal = res.getString(i)
          println(resmd.getColumnName(i) + ": " + colVal + ",")
        }
      }
      //println("")
    }
  }

  // main
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("Welcome to the application system.\n"))
      _ <- IO(iniDB())
      //val id = List(1,2,3,4,5)
      //_ <- IO(insert(2,"name","gender", "2022-03-05","email",
      //  "university_name", "location", "qualification", "major", "2022-03-01", "2022-03-05", 90,5,5,1,
      //  "occupation", "2022-01-01", "2022-02-01", "company_name",
      //  "IELTS","2020-03-01",7,8,7,8,7))
      //_ <- IO(delete(1))
      //_ <- IO(update(2,"education", "gpa", 4.0))
      _ <- IO(view(2,"education"))
    } yield ExitCode.Success
}
