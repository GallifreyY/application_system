import java.sql.ResultSet

object Score {
    private val connection = dataBase.connection.getConnection

    def overallScore(): Unit = {
    val sql_statement = connection.createStatement()
    val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, e.university_rank, e.gpa, " +
        "em.employed_from, em.employed_to, l.score, " +
        "e.gpa*0.25 +(100-e.university_rank)*0.25 + " +
        "(CASE " +
        "WHEN datediff(em.employed_to, em.employed_from) > 365 THEN 25 " +
        "WHEN datediff(em.employed_to, em.employed_from) > 180 THEN 20 " +
        "WHEN datediff(em.employed_to, em.employed_from) < 30 THEN 0 " +
        "ELSE 15 END )" +
        "+ (CASE l.exam_type WHEN \"TOEFL\" THEN l.score/4.8 " +
        "WHEN \"IELTS\" THEN l.score*25/9 ELSE 0 END) AS overall_score " +
        "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
        "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
        "ORDER BY overall_score DESC;")
    println("(Applicant ID,Overall Evaluation)")
    while (result.next())
        println((result.getInt("applicant_id"), result.getInt("overall_score")))

    result.close()
    sql_statement.close()
    }

    def academicScore(): Unit = {
        val sql_statement = connection.createStatement()
        val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, " +
          "em.employed_from, em.employed_to, l.score, " +
          "e.gpa AS academic_score " +
          "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
          "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
          "ORDER BY academic_score DESC;")
        println("(Applicant ID,Academic Score)")
        while (result.next())
            println((result.getInt("applicant_id"), result.getInt("academic_score")))

        result.close()
        sql_statement.close()


    }
    def universityScore(): Unit = {
        val sql_statement = connection.createStatement()
        val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, e.university_rank AS university_rank " +
          "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
          "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
          "ORDER BY university_rank;")
        println("(Applicant ID,University Rank)")
        while (result.next())
            println((result.getInt("applicant_id"), result.getInt("university_rank")))

        result.close()
        sql_statement.close()
    }

    def workScore(): Unit = {
        val sql_statement = connection.createStatement()
        val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, e.university_rank, e.gpa, " +
          "(CASE " +
          "WHEN datediff(em.employed_to, em.employed_from) > 365 THEN 100 " +
          "WHEN datediff(em.employed_to, em.employed_from) > 180 THEN 80 " +
          "WHEN datediff(em.employed_to, em.employed_from) < 30 THEN 60 " +
          "ELSE 70 END ) " +
          "AS work_score " +
          "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
          "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
          "ORDER BY work_score DESC;")
        println("(Applicant ID,Employment Score)")
        while (result.next())
            println((result.getInt("applicant_id"), result.getInt("work_score")))

        result.close()
        sql_statement.close()
    }

    def languageScore(): Unit = {
        val sql_statement = connection.createStatement()
        val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, " +
          "(CASE l.exam_type WHEN \"TOEFL\" THEN l.score/1.2 " +
          "WHEN \"IELTS\" THEN l.score*100/9 ELSE 0 END) AS language_score " +
          "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
          "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
          "ORDER BY language_score DESC;")
        println("(Applicant ID,Language Score)")
        while (result.next())
            println((result.getInt("applicant_id"), result.getInt("language_score")))

        result.close()
        sql_statement.close()
    }
}