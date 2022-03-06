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

  private def delete(applicant_id: Int): Unit = {
    val sql_statement = connection.createStatement()
    sql_statement.executeUpdate(s"DELETE FROM education WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM employment WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM language WHERE applicant_id = ${applicant_id};")
    sql_statement.executeUpdate(s"DELETE FROM applicants WHERE applicant_id = ${applicant_id};")
  }

  private def update(applicant_id: Int,table: String, column: String, new_value: Any): Unit = {
    val sql_statement = connection.createStatement()
    new_value match {
      case new_value: String => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = '${new_value}' WHERE applicant_id = ${applicant_id};")
      case new_value: Int => sql_statement.executeUpdate(s"UPDATE ${table} SET ${column} = ${new_value} WHERE applicant_id = ${applicant_id};")
      case _ => println("Please input either a string or an int.")
    }
  }

  private def view(applicant_id: Int,table:String): Unit = {
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
    }
  }

  private def interface(): Unit = {
  println("Please select from the following operations: \n" +
    "1. Submit your applications: [Press 1]\n" + 
    "2. Delete your applications: [Press 2]\n" + 
    "3. Modify your applications: [Press 3]\n" +
    "4. View your applications: [Press 4]\n" +
    "5. Exit: [Press 0]")
    val userinput = readInt()
    userinput match {
      case 1 =>
        Insert.insertoDB();
        interface()
      case 2 =>
        delete(1)
        interface()
      case 3 =>
        update(2,"education", "gpa", 4.0)
        interface()
      case 4 =>
        view(2,"education")
        interface()
      case 0 =>
        println("Bye")
      case _ =>
        println("Invaild. Please enter number from 0-4 again.")
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
