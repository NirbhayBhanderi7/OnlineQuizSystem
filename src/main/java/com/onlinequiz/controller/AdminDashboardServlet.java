package com.onlinequiz.controller;
import com.onlinequiz.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(req.getSession().getAttribute("admin")==null){resp.sendRedirect(req.getContextPath()+"/admin/login.jsp");return;}
        try{
            req.setAttribute("studentCount",new StudentService().count());
            req.setAttribute("quizCount",new QuizService().count());
            req.setAttribute("resultCount",new ResultService().count());
            req.setAttribute("quizzes",new QuizService().findAll());
            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req,resp);
        }catch(Exception e){ req.setAttribute("error", "Unable to load admin dashboard: " + e.getMessage()); req.setAttribute("studentCount", 0); req.setAttribute("quizCount", 0); req.setAttribute("resultCount", 0); req.setAttribute("quizzes", new java.util.ArrayList<com.onlinequiz.model.Quiz>()); req.getRequestDispatcher("/admin/dashboard.jsp").forward(req,resp); }
    }
}
