package com.onlinequiz.controller;

import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteQuiz")
public class DeleteQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            new QuizService().delete(quizId);
            resp.sendRedirect(req.getContextPath() + "/adminDashboard?deleted=1");
        } catch (Exception e) {
            throw new ServletException("Unable to delete quiz.", e);
        }
    }
}
