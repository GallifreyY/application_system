import java.awt.Choice
import java.sql.ResultSet
import cats.effect.*
import cats.syntax.all.*
import java.lang.Boolean

import java.util.Date
import io.StdIn.*

object Main extends IOApp {
  private val connection = dataBase.connection.getConnection

  //database ini
  private def iniDB(): Unit = {
    println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS applicants(applicant_id CHAR(9) NOT NULL, " +
    "name VARCHAR(64) NOT NULL, gender VARCHAR(64), birthday DATE, " +
    "email VARCHAR(64), PRIMARY KEY(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS education(applicant_id CHAR(9) NOT NULL, " +
    "university_rank VARCHAR(64) NOT NULL, location VARCHAR(64), qualification VARCHAR(64), " +
    "major VARCHAR(64), start_date DATE, end_date DATE, best_score INT, gpa DECIMAL(10, 0), " +
    "ranking INT, sponsorship INT, PRIMARY KEY(applicant_id), FOREIGN KEY (applicant_id) "+
    "REFERENCES applicants(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS employment(applicant_id CHAR(9) NOT NULL, "+
    "occupation VARCHAR(64), employed_from DATE, employed_to DATE, company_name VARCHAR(64) NOT NULL, "+
    "PRIMARY KEY(applicant_id), FOREIGN KEY(applicant_id) REFERENCES applicants(applicant_id));")
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS language(applicant_id CHAR(9) NOT NULL, exam_type VARCHAR(64), "+
    "exam_date DATE, score INT, listening INT, reading INT, speaking INT, writing INT, PRIMARY KEY(applicant_id), "+
    "FOREIGN KEY (applicant_id) REFERENCES applicants(applicant_id));")
    }


  private def interface(): Unit = {
  println("Please select from the following operations: \n" +
    "1. Submit applications: [Press 1]\n" +
    "2. Delete applications: [Press 2]\n" +
    "3. Modify applications: [Press 3]\n" +
    "4. View applications: [Press 4]\n" +
    "5. Evaluate current applicants: [Press 5]\n" +
    "6. Exit: [Press 0]")
    val userinput = readInt()
    userinput match {
      case 1 =>
        Insert.insertoDB()
        interface()
      case 2 =>
        Delete.deletetoDB()
        interface()
      case 3 =>
        Update.updatetoDB()
        interface()
      case 4 =>
        View.viewtoDB()
        interface()
      case 5 =>
        Score.overallScore()
        Score.academicScore()
        Score.universityScore()
        Score.workScore()
        Score.languageScore()
        interface()
      case 0 =>
        println("Bye")
      case _ =>
        println("Invaild. Please enter number from 0-5 again.")
        interface()
    }
  }
  // main
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("Welcome to the application system.\n"))
      _ <- IO(iniDB())
      _ <- IO(interface())
    } yield ExitCode.Success
}
