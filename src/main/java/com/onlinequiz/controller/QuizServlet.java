package com.onlinequiz.controller;

import com.onlinequiz.model.Quiz;
import com.onlinequiz.service.QuestionService;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("student") == null) {
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
            req.setAttribute("quiz", quiz);
            req.setAttribute("questions", new QuestionService().findByQuiz(quizId));
            req.getRequestDispatcher("/quiz.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz ID.");
        } catch (Exception e) {
            req.setAttribute("error", "Unable to load quiz: " + e.getMessage());
            req.setAttribute("questions", new ArrayList<>());
            req.getRequestDispatcher("/quiz.jsp").forward(req, resp);
        }
    }
}
