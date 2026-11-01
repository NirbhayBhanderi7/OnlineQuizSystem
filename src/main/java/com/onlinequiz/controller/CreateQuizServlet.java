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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Admin admin = session == null ? null : (Admin) session.getAttribute("admin");
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String rawTime = req.getParameter("timePerQuestionMinutes");
            if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Quiz title is required.");
            if (rawTime == null || rawTime.trim().isEmpty()) throw new IllegalArgumentException("Time per question is required.");
            int perQuestion = Integer.parseInt(rawTime);
            if (perQuestion < 1 || perQuestion > 180) throw new IllegalArgumentException("Time per question must be between 1 and 180 minutes.");
            Quiz q = new Quiz();
            q.setTitle(title.trim());
            q.setDescription(description == null ? "" : description.trim());
            q.setTimePerQuestionMinutes(perQuestion);
            q.setCreatedBy(admin.getAdminId());
            int id = new QuizService().create(q);
            resp.sendRedirect(req.getContextPath() + "/admin/addQuestion.jsp?quizId=" + id);
        } catch (Exception e) {
            req.setAttribute("error", "Unable to create quiz: " + e.getMessage());
            req.getRequestDispatcher("/admin/createQuiz.jsp").forward(req, resp);
        }
    }
}
