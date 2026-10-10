package com.onlinequiz.service;
import com.onlinequiz.model.Quiz;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class QuizService {
    public int create(Quiz q) throws SQLException {
        String sql="INSERT INTO quizzes(title,description,duration,total_questions,created_by) VALUES(?,?,?,0,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,q.getTitle());p.setString(2,q.getDescription());p.setInt(3,q.getDuration());p.setInt(4,q.getCreatedBy());p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){if(r.next()) return r.getInt(1);}
        } return 0;
    }
    public Quiz find(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM quizzes WHERE quiz_id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(r.next())return map(r);}
        }return null;
    }
    public List<Quiz> findAll() throws SQLException {
        List<Quiz> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM quizzes ORDER BY quiz_id DESC");ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        return list;
    }
    public int count() throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM quizzes");ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}
    }
    public boolean update(int quizId, String title, String description, int duration) throws SQLException {
        String sql = "UPDATE quizzes SET title=?, description=?, duration=? WHERE quiz_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, title); p.setString(2, description); p.setInt(3, duration); p.setInt(4, quizId);
            return p.executeUpdate() == 1;
        }
    }

    public boolean delete(int quizId) throws SQLException {
        String sql = "DELETE FROM quizzes WHERE quiz_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, quizId);
            return p.executeUpdate() == 1;
        }
    }

    public void incrementQuestions(int quizId) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE quizzes SET total_questions=total_questions+1 WHERE quiz_id=?")){p.setInt(1,quizId);p.executeUpdate();}
    }
    private Quiz map(ResultSet r)throws SQLException{
        Quiz q=new Quiz();q.setQuizId(r.getInt("quiz_id"));q.setTitle(r.getString("title"));q.setDescription(r.getString("description"));q.setDuration(r.getInt("duration"));q.setTotalQuestions(r.getInt("total_questions"));q.setCreatedBy(r.getInt("created_by"));return q;
    }
}
