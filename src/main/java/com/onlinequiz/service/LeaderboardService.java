package com.onlinequiz.service;
import com.onlinequiz.model.Leaderboard;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class LeaderboardService {
    public List<Leaderboard> getLeaderboard(int quizId)throws SQLException{
        List<Leaderboard> list=new ArrayList<>();
        String sql="SELECT r.student_id,r.quiz_id,r.score,s.name student_name,q.title quiz_title FROM results r JOIN students s ON r.student_id=s.student_id JOIN quizzes q ON r.quiz_id=q.quiz_id WHERE r.quiz_id=? ORDER BY r.score DESC,r.attempted_at ASC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,quizId);try(ResultSet r=p.executeQuery()){int rank=1;while(r.next()){Leaderboard l=new Leaderboard();l.setStudentId(r.getInt("student_id"));l.setQuizId(quizId);l.setScore(r.getInt("score"));l.setRankPosition(rank++);l.setStudentName(r.getString("student_name"));l.setQuizTitle(r.getString("quiz_title"));list.add(l);}}}
        return list;
    }
}
