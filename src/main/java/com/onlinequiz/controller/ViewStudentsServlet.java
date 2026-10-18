package com.onlinequiz.controller;
import com.onlinequiz.service.StudentService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/viewStudents")
public class ViewStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(req.getSession().getAttribute("admin")==null){resp.sendRedirect(req.getContextPath()+"/admin/login.jsp");return;}
        try{req.setAttribute("students",new StudentService().findAll());req.getRequestDispatcher("/admin/viewStudents.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}
    }
}
