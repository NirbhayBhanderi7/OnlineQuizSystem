package com.onlinequiz.service;
import com.onlinequiz.model.Option;
import com.onlinequiz.util.DBConnection;
import java.sql.*;
import java.util.*;

public class OptionService {
    public List<Option> findByQuestion(int questionId)throws SQLException{
        List<Option> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM options WHERE question_id=? ORDER BY option_id")){
            p.setInt(1,questionId);try(ResultSet r=p.executeQuery()){while(r.next()){Option o=new Option();o.setOptionId(r.getInt("option_id"));o.setQuestionId(questionId);o.setOptionText(r.getString("option_text"));o.setCorrect(r.getBoolean("is_correct"));list.add(o);}}
        }return list;
    }
}
