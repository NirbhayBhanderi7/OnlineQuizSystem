package com.onlinequiz.controller;

import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateQuiz")
public class UpdateQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            int perQuestion = Integer.parseInt(req.getParameter("timePerQuestionMinutes"));
            if (title == null || title.trim().isEmpty() || perQuestion < 1 || perQuestion > 180) {
                throw new IllegalArgumentException("A title and time per question between 1 and 180 minutes are required.");
            }
            if (!new QuizService().update(quizId, title.trim(), description, perQuestion)) {
                throw new IllegalArgumentException("Quiz not found.");
            }
            resp.sendRedirect(req.getContextPath() + "/adminDashboard?updated=1");
        } catch (Exception e) {
            req.setAttribute("error", "Unable to update quiz: " + e.getMessage());
            req.getRequestDispatcher("/admin/editQuiz.jsp").forward(req, resp);
        }
    }
}
