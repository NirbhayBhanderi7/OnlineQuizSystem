package com.onlinequiz.controller;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(req.getSession().getAttribute("student")==null){resp.sendRedirect(req.getContextPath()+"/login.jsp");return;}
        try{req.setAttribute("quizzes",new QuizService().findAll());req.getRequestDispatcher("/dashboard.jsp").forward(req,resp);}
        catch(Exception e){ req.setAttribute("error", "Unable to load quizzes: " + e.getMessage()); req.setAttribute("quizzes", new java.util.ArrayList<com.onlinequiz.model.Quiz>()); req.getRequestDispatcher("/dashboard.jsp").forward(req,resp); }
    }
}
