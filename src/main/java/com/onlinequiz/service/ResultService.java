package com.onlinequiz.service;
import com.onlinequiz.model.Result;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ResultService {
    public int save(int studentId,int quizId,int score,int total,int correct,int wrong)throws SQLException{
        String sql="INSERT INTO results(student_id,quiz_id,score,total_marks,correct_answers,wrong_answers) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setInt(1,studentId);p.setInt(2,quizId);p.setInt(3,score);p.setInt(4,total);p.setInt(5,correct);p.setInt(6,wrong);p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){if(r.next())return r.getInt(1);}
        }return 0;
    }
    public Result find(int id)throws SQLException{
        String sql="SELECT r.*,s.name student_name,q.title quiz_title FROM results r JOIN students s ON r.student_id=s.student_id JOIN quizzes q ON r.quiz_id=q.quiz_id WHERE r.result_id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(r.next())return map(r);}}return null;
    }
    public List<Result> findAll()throws SQLException{
        List<Result> list=new ArrayList<>();
        String sql="SELECT r.*,s.name student_name,q.title quiz_title FROM results r JOIN students s ON r.student_id=s.student_id JOIN quizzes q ON r.quiz_id=q.quiz_id ORDER BY r.score DESC,r.attempted_at ASC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}return list;
    }
    public int count()throws SQLException{try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM results");ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}}
    private Result map(ResultSet r)throws SQLException{
        Result x=new Result();x.setResultId(r.getInt("result_id"));x.setStudentId(r.getInt("student_id"));x.setQuizId(r.getInt("quiz_id"));x.setScore(r.getInt("score"));x.setTotalMarks(r.getInt("total_marks"));x.setCorrectAnswers(r.getInt("correct_answers"));x.setWrongAnswers(r.getInt("wrong_answers"));x.setStudentName(r.getString("student_name"));x.setQuizTitle(r.getString("quiz_title"));x.setAttemptedAt(r.getString("attempted_at"));return x;
    }
}
