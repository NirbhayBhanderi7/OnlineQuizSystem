package com.onlinequiz.service;

import com.onlinequiz.model.*;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class QuestionService {
    public int addQuestion(int quizId, String text, int marks, String[] optionTexts, int correctIndex) throws SQLException {
        if (optionTexts == null || optionTexts.length != 4 || correctIndex < 0 || correctIndex > 3) {
            throw new IllegalArgumentException("Exactly four options and one correct option are required.");
        }
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                int qid;
                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO questions(quiz_id,question_text,marks) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    p.setInt(1, quizId);
                    p.setString(2, text);
                    p.setInt(3, marks);
                    p.executeUpdate();
                    try (ResultSet r = p.getGeneratedKeys()) {
                        if (!r.next()) throw new SQLException("Could not create question.");
                        qid = r.getInt(1);
                    }
                }
                insertOptions(c, qid, optionTexts, correctIndex);
                try (PreparedStatement p = c.prepareStatement(
                        "UPDATE quizzes SET duration=(total_questions+1)*time_per_question_minutes, total_questions=total_questions+1 WHERE quiz_id=?")) {
                    p.setInt(1, quizId);
                    if (p.executeUpdate() != 1) throw new SQLException("Quiz not found.");
                }
                c.commit();
                return qid;
            } catch (Exception e) {
                c.rollback();
                if (e instanceof SQLException) throw (SQLException)e;
                throw new SQLException("Unable to add question.", e);
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public boolean updateQuestion(int quizId, int questionId, String text, int marks,
                                   String[] optionTexts, int correctIndex) throws SQLException {
        if (optionTexts == null || optionTexts.length != 4 || correctIndex < 0 || correctIndex > 3) {
            throw new IllegalArgumentException("Exactly four options and one correct option are required.");
        }
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                int updated;
                try (PreparedStatement p = c.prepareStatement(
                        "UPDATE questions SET question_text=?, marks=? WHERE question_id=? AND quiz_id=?")) {
                    p.setString(1, text);
                    p.setInt(2, marks);
                    p.setInt(3, questionId);
                    p.setInt(4, quizId);
                    updated = p.executeUpdate();
                }
                if (updated != 1) {
                    c.rollback();
                    return false;
                }
                try (PreparedStatement p = c.prepareStatement("DELETE FROM options WHERE question_id=?")) {
                    p.setInt(1, questionId);
                    p.executeUpdate();
                }
                insertOptions(c, questionId, optionTexts, correctIndex);
                c.commit();
                return true;
            } catch (Exception e) {
                c.rollback();
                if (e instanceof SQLException) throw (SQLException)e;
                throw new SQLException("Unable to update question.", e);
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    private void insertOptions(Connection c, int questionId, String[] optionTexts, int correctIndex) throws SQLException {
        try (PreparedStatement p = c.prepareStatement(
                "INSERT INTO options(question_id,option_text,is_correct) VALUES(?,?,?)")) {
            for (int i = 0; i < optionTexts.length; i++) {
                if (optionTexts[i] == null || optionTexts[i].trim().isEmpty()) {
                    throw new IllegalArgumentException("All four options are required.");
                }
                p.setInt(1, questionId);
                p.setString(2, optionTexts[i].trim());
                p.setBoolean(3, i == correctIndex);
                p.addBatch();
            }
            p.executeBatch();
        }
    }

    public List<Question> findByQuiz(int quizId) throws SQLException {
        List<Question> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT * FROM questions WHERE quiz_id=? ORDER BY question_id")) {
            p.setInt(1, quizId);
            try (ResultSet r = p.executeQuery()) {
                OptionService os = new OptionService();
                while (r.next()) {
                    Question q = new Question();
                    q.setQuestionId(r.getInt("question_id"));
                    q.setQuizId(quizId);
                    q.setQuestionText(r.getString("question_text"));
                    q.setMarks(r.getInt("marks"));
                    q.setOptions(os.findByQuestion(q.getQuestionId()));
                    list.add(q);
                }
            }
        }
        return list;
    }

    public Question findQuestion(int quizId, int questionId) throws SQLException {
        for (Question q : findByQuiz(quizId)) {
            if (q.getQuestionId() == questionId) return q;
        }
        return null;
    }
}
