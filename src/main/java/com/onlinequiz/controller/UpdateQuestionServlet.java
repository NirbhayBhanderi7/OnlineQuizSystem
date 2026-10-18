package com.onlinequiz.controller;

import com.onlinequiz.service.QuestionService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/updateQuestion")
public class UpdateQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        String quizIdRaw = req.getParameter("quizId");
        String questionIdRaw = req.getParameter("questionId");
        try {
            int quizId = Integer.parseInt(quizIdRaw);
            int questionId = Integer.parseInt(questionIdRaw);
            String text = req.getParameter("questionText");
            int marks = Integer.parseInt(req.getParameter("marks"));
            int correct = Integer.parseInt(req.getParameter("correct"));
            String[] options = {req.getParameter("option1"), req.getParameter("option2"),
                    req.getParameter("option3"), req.getParameter("option4")};
            if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException("Question text is required.");
            if (marks < 1) throw new IllegalArgumentException("Marks must be at least 1.");
            if (correct < 0 || correct > 3) throw new IllegalArgumentException("Select one correct option.");
            for (String option : options) {
                if (option == null || option.trim().isEmpty()) throw new IllegalArgumentException("All four options are required.");
            }
            boolean updated = new QuestionService().updateQuestion(quizId, questionId, text.trim(), marks, options, correct);
            if (!updated) throw new IllegalArgumentException("The selected question was not found in this quiz.");
            resp.sendRedirect(req.getContextPath() + "/manageQuestions?quizId=" + quizId + "&questionId=" + questionId + "&updated=1");
        } catch (Exception e) {
            if (quizIdRaw != null && quizIdRaw.matches("\\d+")) {
                String message = URLEncoder.encode("Unable to update question: " + e.getMessage(), StandardCharsets.UTF_8.name());
                String selected = questionIdRaw != null && questionIdRaw.matches("\\d+") ? "&questionId=" + questionIdRaw : "";
                resp.sendRedirect(req.getContextPath() + "/manageQuestions?quizId=" + quizIdRaw + selected + "&error=" + message);
            } else {
                resp.sendRedirect(req.getContextPath() + "/adminDashboard?error=invalidQuestion");
            }
        }
    }
}
