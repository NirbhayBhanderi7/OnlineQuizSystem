package com.onlinequiz.service;
import com.onlinequiz.model.*;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class QuestionService {
    public int addQuestion(int quizId,String text,int marks,String[] optionTexts,int correctIndex)throws SQLException{
        Connection c=DBConnection.getConnection();
        try{
            c.setAutoCommit(false);
            int qid;
            try(PreparedStatement p=c.prepareStatement("INSERT INTO questions(quiz_id,question_text,marks) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){
                p.setInt(1,quizId);p.setString(2,text);p.setInt(3,marks);p.executeUpdate();
                try(ResultSet r=p.getGeneratedKeys()){r.next();qid=r.getInt(1);}
            }
            try(PreparedStatement p=c.prepareStatement("INSERT INTO options(question_id,option_text,is_correct) VALUES(?,?,?)")){
                for(int i=0;i<optionTexts.length;i++){p.setInt(1,qid);p.setString(2,optionTexts[i]);p.setBoolean(3,i==correctIndex);p.addBatch();}
                p.executeBatch();
            }
            try(PreparedStatement p=c.prepareStatement("UPDATE quizzes SET total_questions=total_questions+1 WHERE quiz_id=?")){p.setInt(1,quizId);p.executeUpdate();}
            c.commit();return qid;
        }catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(true);c.close();}
    }
    public List<Question> findByQuiz(int quizId)throws SQLException{
        List<Question> list=new ArrayList<>();
        String sql="SELECT * FROM questions WHERE quiz_id=? ORDER BY question_id";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,quizId);try(ResultSet r=p.executeQuery()){
            OptionService os=new OptionService();
            while(r.next()){Question q=new Question();q.setQuestionId(r.getInt("question_id"));q.setQuizId(quizId);q.setQuestionText(r.getString("question_text"));q.setMarks(r.getInt("marks"));q.setOptions(os.findByQuestion(q.getQuestionId()));list.add(q);}
        }}return list;
    }
}
