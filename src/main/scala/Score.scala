import java.sql.ResultSet

object Score {
    private val connection = dataBase.connection.getConnection

    def academicRecord(): Unit = {
    val sql_statement = connection.createStatement()
    val result: ResultSet =sql_statement.executeQuery("SELECT e.applicant_id, e.university_rank, e.gpa, " +
        "em.employed_from, em.employed_to, l.score, " +
        "e.gpa*0.25 +(100-e.university_rank)*0.25 + " +
        "(CASE " +
        "WHEN datediff(em.employed_to, em.employed_from) > 365 THEN 25 " +
        "WHEN datediff(em.employed_to, em.employed_from) > 180 THEN 20 " +
        "WHEN datediff(em.employed_to, em.employed_from) < 30 THEN 0 " +
        "ELSE 15 END )" +
        "+l.score/4.8 AS overall_score " +
        "FROM education AS e INNER JOIN employment AS em ON e.applicant_id = em.applicant_id " +
        "INNER JOIN language AS l on e.applicant_id = l.applicant_id " +
        "ORDER BY overall_score DESC;")
    println("(applicant_id,overall_score)")
    while (result.next())
        println((result.getInt("applicant_id"), result.getInt("overall_score")))

    result.close()
    sql_statement.close()
    }
}