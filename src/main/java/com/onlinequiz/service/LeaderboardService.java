package com.onlinequiz.service;

import com.onlinequiz.model.Leaderboard;
import com.onlinequiz.util.DBConnection;

import java.sql.*;
import java.util.*;

public class LeaderboardService {

    /*
     * Rebuild leaderboard for a particular quiz.
     *
     * Data is taken from results table.
     * Students are sorted from highest score to lowest score.
     * Rank is then stored permanently in leaderboard table.
     */
    public void updateLeaderboard(int quizId) throws SQLException {

        String deleteSql =
                "DELETE FROM leaderboard WHERE quiz_id=?";

        String selectSql =
                "SELECT r.student_id, r.quiz_id, r.score " +
                "FROM results r " +
                "WHERE r.quiz_id=? " +
                "ORDER BY r.score DESC, r.attempted_at ASC";

        String insertSql =
                "INSERT INTO leaderboard " +
                "(student_id, quiz_id, score, rank_position) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection()) {

            /*
             * Remove old rankings for this quiz.
             */
            try (PreparedStatement delete =
                         c.prepareStatement(deleteSql)) {

                delete.setInt(1, quizId);
                delete.executeUpdate();
            }

            /*
             * Read all results in ranking order.
             */
            try (PreparedStatement select =
                         c.prepareStatement(selectSql)) {

                select.setInt(1, quizId);

                try (ResultSet rs = select.executeQuery()) {

                    int rank = 1;

                    try (PreparedStatement insert =
                                 c.prepareStatement(insertSql)) {

                        while (rs.next()) {

                            insert.setInt(
                                    1,
                                    rs.getInt("student_id")
                            );

                            insert.setInt(
                                    2,
                                    rs.getInt("quiz_id")
                            );

                            insert.setInt(
                                    3,
                                    rs.getInt("score")
                            );

                            insert.setInt(
                                    4,
                                    rank
                            );

                            insert.executeUpdate();

                            rank++;
                        }
                    }
                }
            }
        }
    }


    /*
     * Get leaderboard from the leaderboard table.
     */
    public List<Leaderboard> getLeaderboard(int quizId)
            throws SQLException {

        List<Leaderboard> list =
                new ArrayList<>();

        String sql =
                "SELECT l.leaderboard_id, " +
                "l.student_id, " +
                "l.quiz_id, " +
                "l.score, " +
                "l.rank_position, " +
                "s.name AS student_name, " +
                "q.title AS quiz_title " +
                "FROM leaderboard l " +
                "JOIN students s " +
                "ON l.student_id = s.student_id " +
                "JOIN quizzes q " +
                "ON l.quiz_id = q.quiz_id " +
                "WHERE l.quiz_id=? " +
                "ORDER BY l.rank_position ASC";

        try (Connection c =
                     DBConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, quizId);

            try (ResultSet r = p.executeQuery()) {

                while (r.next()) {

                    Leaderboard l =
                            new Leaderboard();

                    l.setLeaderboardId(
                            r.getInt("leaderboard_id")
                    );

                    l.setStudentId(
                            r.getInt("student_id")
                    );

                    l.setQuizId(
                            r.getInt("quiz_id")
                    );

                    l.setScore(
                            r.getInt("score")
                    );

                    l.setRankPosition(
                            r.getInt("rank_position")
                    );

                    l.setStudentName(
                            r.getString("student_name")
                    );

                    l.setQuizTitle(
                            r.getString("quiz_title")
                    );

                    list.add(l);
                }
            }
        }

        return list;
    }
}