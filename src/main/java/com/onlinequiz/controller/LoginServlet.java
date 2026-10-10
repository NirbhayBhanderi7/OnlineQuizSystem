package com.onlinequiz.controller;
import com.onlinequiz.model.Student;
import com.onlinequiz.service.StudentService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            String email=req.getParameter("email"), password=req.getParameter("password");
            Student s=new StudentService().login(email,password);
            if(s!=null){req.getSession().setAttribute("student",s);resp.sendRedirect(req.getContextPath()+"/dashboard");}
            else{req.setAttribute("error","Invalid email or password.");req.getRequestDispatcher("/login.jsp").forward(req,resp);}
        }catch(Exception e){req.setAttribute("error","Database error. Check MySQL and DBConnection settings.");req.getRequestDispatcher("/login.jsp").forward(req,resp);}
    }
}
