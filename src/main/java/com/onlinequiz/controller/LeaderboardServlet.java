package com.onlinequiz.controller;

import com.onlinequiz.service.LeaderboardService;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.onlinequiz.model.Quiz;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        try {
            String id = req.getParameter("quizId");
            int quizId;
            if (id == null || id.trim().isEmpty()) {
                List<Quiz> quizzes = new QuizService().findAll();
                quizId = quizzes.isEmpty() ? 0 : quizzes.get(0).getQuizId();
            } else {
                quizId = Integer.parseInt(id);
            }
            req.setAttribute("quizId", quizId);
            req.setAttribute("leaderboard", new LeaderboardService().getLeaderboard(quizId));
            req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz ID.");
        } catch (Exception e) {
            throw new ServletException("Unable to load leaderboard.", e);
        }
    }
}
