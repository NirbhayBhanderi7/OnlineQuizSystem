package com.onlinequiz.controller;

import com.onlinequiz.model.*;
import com.onlinequiz.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/submitQuiz")
public class SubmitQuizServlet extends HttpServlet {

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Student s = (Student) req.getSession().getAttribute("student");

        if (s == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {

            int quizId = Integer.parseInt(req.getParameter("quizId"));

            List<Question> qs = new QuestionService().findByQuiz(quizId);

            int score = 0;
            int total = 0;
            int correct = 0;
            int wrong = 0;

            for (Question q : qs) {

                total += q.getMarks();

                String selected =
                        req.getParameter("q_" + q.getQuestionId());

                boolean ok = false;

                if (selected != null) {

                    for (Option o : q.getOptions()) {

                        if (o.isCorrect()
                                && selected.equals(
                                String.valueOf(o.getOptionId()))) {

                            ok = true;
                            break;
                        }
                    }
                }

                if (ok) {

                    score += q.getMarks();
                    correct++;

                } else if (selected != null) {

                    wrong++;
                }
            }

            // 1. Save result
            ResultService resultService = new ResultService();

            int resultId = resultService.save(
                    s.getStudentId(),
                    quizId,
                    score,
                    total,
                    correct,
                    wrong
            );

            // 2. Update leaderboard
            LeaderboardService leaderboardService =
                    new LeaderboardService();

            leaderboardService.updateLeaderboard(quizId);

            // 3. Show result page
            req.setAttribute(
                    "result",
                    resultService.find(resultId)
            );

            req.getRequestDispatcher("/result.jsp")
                    .forward(req, resp);

        } catch (IOException | NumberFormatException | SQLException | ServletException e) {

            throw new ServletException(
                    "Error while submitting quiz.",
                    e
            );
        }
    }
}