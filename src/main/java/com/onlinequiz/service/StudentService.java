package com.onlinequiz.service;
import com.onlinequiz.model.Student;
import com.onlinequiz.util.DBConnection;
import java.sql.*;

public class StudentService {
    public boolean register(Student s) throws SQLException {
        String sql="INSERT INTO students(name,email,password,course) VALUES(?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,s.getName()); p.setString(2,s.getEmail()); p.setString(3,s.getPassword()); p.setString(4,s.getCourse());
            return p.executeUpdate()==1;
        }
    }
    public Student login(String email,String password) throws SQLException {
        String sql="SELECT * FROM students WHERE email=? AND password=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,email); p.setString(2,password);
            try(ResultSet r=p.executeQuery()){
                if(r.next()){ Student s=new Student(); s.setStudentId(r.getInt("student_id")); s.setName(r.getString("name")); s.setEmail(r.getString("email")); s.setCourse(r.getString("course")); return s; }
            }
        }
        return null;
    }
    public boolean delete(int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, studentId);
            return p.executeUpdate() == 1;
        }
    }

    public int count() throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM students"); ResultSet r=p.executeQuery()){r.next(); return r.getInt(1);}
    }
    public java.util.List<Student> findAll() throws SQLException {
        java.util.List<Student> list=new java.util.ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT * FROM students ORDER BY student_id DESC"); ResultSet r=p.executeQuery()){
            while(r.next()){Student s=new Student(); s.setStudentId(r.getInt("student_id")); s.setName(r.getString("name")); s.setEmail(r.getString("email")); s.setCourse(r.getString("course")); list.add(s);}
        } return list;
    }
}
