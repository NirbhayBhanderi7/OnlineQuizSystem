package com.onlinequiz.controller;
import com.onlinequiz.model.Admin;
import com.onlinequiz.model.Quiz;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/createQuiz")
public class CreateQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        Admin a=(Admin)req.getSession().getAttribute("admin");
        if(a==null){resp.sendRedirect(req.getContextPath()+"/admin/login.jsp");return;}
        try{
            Quiz q=new Quiz();q.setTitle(req.getParameter("title"));q.setDescription(req.getParameter("description"));q.setDuration(Integer.parseInt(req.getParameter("duration")));q.setCreatedBy(a.getAdminId());
            int id=new QuizService().create(q);resp.sendRedirect(req.getContextPath()+"/admin/addQuestion.jsp?quizId="+id);
        }catch(Exception e){req.setAttribute("error","Unable to create quiz: "+e.getMessage());req.getRequestDispatcher("/admin/createQuiz.jsp").forward(req,resp);}
    }
}
