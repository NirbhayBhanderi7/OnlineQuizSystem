package com.onlinequiz.controller;

import com.onlinequiz.service.QuestionService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addQuestion")
public class AddQuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        String quizId = req.getParameter("quizId");
        try {
            int qid = Integer.parseInt(quizId);
            String questionText = req.getParameter("questionText");
            String[] options = { req.getParameter("option1"), req.getParameter("option2"), req.getParameter("option3"), req.getParameter("option4") };
            int correct = Integer.parseInt(req.getParameter("correct"));
            int marks = Integer.parseInt(req.getParameter("marks"));
            if (questionText == null || questionText.trim().isEmpty() || correct < 0 || correct > 3 || marks < 1) {
                throw new IllegalArgumentException("Please enter valid question details.");
            }
            for (String option : options) {
                if (option == null || option.trim().isEmpty()) {
                    throw new IllegalArgumentException("All four options are required.");
                }
            }
            new QuestionService().addQuestion(qid, questionText.trim(), marks, options, correct);
            resp.sendRedirect(req.getContextPath() + "/admin/addQuestion.jsp?quizId=" + qid + "&success=1");
        } catch (Exception e) {
            req.setAttribute("error", "Unable to add question: " + e.getMessage());
            req.setAttribute("quizId", quizId);
            req.getRequestDispatcher("/admin/addQuestion.jsp").forward(req, resp);
        }
    }
}
