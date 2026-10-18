package com.onlinequiz.controller;
import com.onlinequiz.model.Student;
import com.onlinequiz.service.StudentService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            String name=req.getParameter("name"),email=req.getParameter("email"),password=req.getParameter("password"),course=req.getParameter("course");
            if(name==null||name.trim().isEmpty()||email==null||email.trim().isEmpty()||password==null||password.trim().isEmpty()){req.setAttribute("error","All required fields must be filled.");req.getRequestDispatcher("/register.jsp").forward(req,resp);return;}
            Student s=new Student();s.setName(name.trim());s.setEmail(email.trim());s.setPassword(password);s.setCourse(course);
            new StudentService().register(s);resp.sendRedirect(req.getContextPath()+"/login.jsp?registered=1");
        }catch(Exception e){req.setAttribute("error","Registration failed. Email may already exist.");req.getRequestDispatcher("/register.jsp").forward(req,resp);}
    }
}
