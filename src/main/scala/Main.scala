import java.awt.Choice
import java.sql.ResultSet
import cats.effect._
import cats.syntax.all.*
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
    sql_statement.executeUpdate("CREATE TABLE IF NOT EXISTS language(applicant_id CHAR(9) NOT NULL, type VARCHAR(64), "+
    "exam_date DATE, score INT, listening INT, reading INT, speaking INT, writing INT, PRIMARY KEY(applicant_id), "+
    "FOREIGN KEY (applicant_id) REFERENCES applicants(applicant_id));")
    }

  private def insert(): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("INSERT INTO applicants VALUES (000000005, 'name', 'gender', '2022-03-05', 'email');")
  }

  private def delete(): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("DELETE FROM applicants WHERE applicant_id = 1;")
  }

  private def update(): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("UPDATE applicants SET name = 'namee' WHERE applicant_id = 2;")
  }

  private def view(): Unit = {
    //println("Start Initializing the Database...")
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate("UPDATE applicants SET name = 'namee' WHERE applicant_id = 2;")
  }

  // main
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("Welcome to the applicantion system.\n"))
      _ <- IO(iniDB())
      _ <- IO(update())
    } yield ExitCode.Success
}
