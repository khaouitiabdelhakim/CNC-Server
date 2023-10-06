package com.abdelhakim.cnc.login.service;

import com.abdelhakim.cnc.login.models.Classement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClassementService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassementService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Classement> calculateSumForStudentsOrderBySum(boolean ascending) {
        AtomicInteger counter = new AtomicInteger(1);
        // Define the SQL query to calculate the sum and join the tables
        String sqlQuery = "SELECT u.*, SUM(n.note * m.coefficient) AS totalSum\n" +
                "FROM Notes n\n" +
                "INNER JOIN Matieres m ON n.id_matiere = m.id\n" +
                "INNER JOIN Users u ON n.id_etudiant = u.id\n" +
                "GROUP BY u.id\n" +
                "ORDER BY totalSum " + (ascending ? "ASC" : "DESC");

        // Execute the query and map the results to the StudentDTO class
        List<Classement> students = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            Classement studentDTO = new Classement();
            studentDTO.setClassement(counter.get());
            studentDTO.setId(rs.getLong("id"));
            studentDTO.setTotal(rs.getDouble("totalSum"));

            counter.getAndIncrement();

            return studentDTO;
        });

        return students;
    }
}

