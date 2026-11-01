package com.onlinequiz.service;
import com.onlinequiz.model.Admin;
import com.onlinequiz.util.DBConnection;
import java.sql.*;

public class AdminService {
    public Admin login(String username,String password) throws SQLException {
        String sql="SELECT * FROM admins WHERE username=? AND password=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,username); p.setString(2,password);
            try(ResultSet r=p.executeQuery()){
                if(r.next()){Admin a=new Admin();a.setAdminId(r.getInt("admin_id"));a.setName(r.getString("name"));a.setUsername(r.getString("username"));return a;}
            }
        } return null;
    }
}
