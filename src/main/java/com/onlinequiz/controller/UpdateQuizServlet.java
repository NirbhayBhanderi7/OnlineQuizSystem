package com.onlinequiz.controller;

import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateQuiz")
public class UpdateQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            int duration = Integer.parseInt(req.getParameter("duration"));
            if (title == null || title.trim().isEmpty() || duration < 1) throw new IllegalArgumentException("Title and a positive duration are required.");
            new QuizService().update(quizId, title.trim(), description, duration);
            resp.sendRedirect(req.getContextPath() + "/adminDashboard?updated=1");
        } catch (Exception e) {
            req.setAttribute("error", "Unable to update quiz: " + e.getMessage());
            req.getRequestDispatcher("/admin/editQuiz.jsp").forward(req, resp);
        }
    }
}
