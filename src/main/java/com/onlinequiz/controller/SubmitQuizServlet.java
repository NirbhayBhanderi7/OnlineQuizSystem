package com.onlinequiz.controller;

import com.onlinequiz.model.*;
import com.onlinequiz.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/submitQuiz")
public class SubmitQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Student student = session == null ? null : (Student) session.getAttribute("student");
        if (student == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?expired=1");
            return;
        }
        try {
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            Quiz quiz = new QuizService().find(quizId);
            if (quiz == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            String startKey = "quizStartTime_" + quizId;
            Object startValue = session.getAttribute(startKey);
            if (!(startValue instanceof Long)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No active attempt was found. Please start the quiz again.");
                return;
            }
            long elapsedSeconds = Math.max(0L, (System.currentTimeMillis() - ((Long) startValue)) / 1000L);
            boolean timedOut = quiz.getDuration() > 0 && elapsedSeconds > (quiz.getDuration() * 60L + 15L);

            List<Question> questions = new QuestionService().findByQuiz(quizId);
            int score = 0, total = 0, correct = 0, wrong = 0;
            for (Question q : questions) {
                total += q.getMarks();
                String selected = timedOut ? null : req.getParameter("q_" + q.getQuestionId());
                boolean isCorrect = false;
                if (selected != null) {
                    for (Option option : q.getOptions()) {
                        if (option.isCorrect() && selected.equals(String.valueOf(option.getOptionId()))) {
                            isCorrect = true;
                            break;
                        }
                    }
                }
                if (isCorrect) {
                    score += q.getMarks();
                    correct++;
                } else if (selected != null) {
                    wrong++;
                }
            }
            int resultId = new ResultService().save(student.getStudentId(), quizId, score, total, correct, wrong);
            session.removeAttribute(startKey);
            session.setMaxInactiveInterval(30 * 60);
            req.setAttribute("result", new ResultService().find(resultId));
            if (timedOut) req.setAttribute("timedOut", Boolean.TRUE);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz ID.");
        } catch (Exception e) {
            throw new ServletException("Unable to submit quiz.", e);
        }
    }
}
