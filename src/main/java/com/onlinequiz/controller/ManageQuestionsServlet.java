package com.onlinequiz.controller;

import com.onlinequiz.model.Question;
import com.onlinequiz.model.Quiz;
import com.onlinequiz.service.QuestionService;
import com.onlinequiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/manageQuestions")
public class ManageQuestionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            Quiz quiz = new QuizService().find(quizId);
            if (quiz == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            List<Question> questions = new QuestionService().findByQuiz(quizId);
            String selected = req.getParameter("questionId");
            Question selectedQuestion = null;
            if (selected != null && !selected.trim().isEmpty()) {
                selectedQuestion = new QuestionService().findQuestion(quizId, Integer.parseInt(selected));
                if (selectedQuestion == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Question not found in this quiz.");
                    return;
                }
            } else if (!questions.isEmpty()) {
                selectedQuestion = questions.get(0);
            }
            req.setAttribute("quiz", quiz);
            req.setAttribute("questions", questions);
            req.setAttribute("selectedQuestion", selectedQuestion);
            req.getRequestDispatcher("/admin/editQuestions.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "A valid quiz and question ID are required.");
        } catch (Exception e) {
            throw new ServletException("Unable to load questions for editing.", e);
        }
    }
}
