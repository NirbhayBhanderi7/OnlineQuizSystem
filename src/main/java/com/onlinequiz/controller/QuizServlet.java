package com.onlinequiz.controller;

import com.onlinequiz.model.Quiz;
import com.onlinequiz.service.QuestionService;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        try {
            String id = req.getParameter("quizId");
            if (id == null || id.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required.");
                return;
            }
            int quizId = Integer.parseInt(id);
            Quiz quiz = new QuizService().find(quizId);
            if (quiz == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            if (quiz.getTotalQuestions() > 0 && quiz.getDuration() <= 0) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Quiz timing is not configured. Contact the administrator.");
                return;
            }
            String startKey = "quizStartTime_" + quizId;
            if (session.getAttribute(startKey) == null) {
                session.setAttribute(startKey, System.currentTimeMillis());
                int attemptTimeout = quiz.getDuration() * 60 + 10 * 60;
                session.setMaxInactiveInterval(Math.max(30 * 60, attemptTimeout));
            }
            long startedAt = (Long) session.getAttribute(startKey);
            int remainingSeconds = Math.max(0, quiz.getDuration() * 60
                    - (int)((System.currentTimeMillis() - startedAt) / 1000L));
            req.setAttribute("remainingSeconds", remainingSeconds);
            req.setAttribute("quiz", quiz);
            req.setAttribute("questions", new QuestionService().findByQuiz(quizId));
            req.getRequestDispatcher("/quiz.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz ID.");
        } catch (Exception e) {
            req.setAttribute("error", "Unable to load quiz. Please try again.");
            req.setAttribute("questions", new ArrayList<>());
            req.getRequestDispatcher("/quiz.jsp").forward(req, resp);
        }
    }
}
