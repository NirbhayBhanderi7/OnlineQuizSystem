<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("student") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Quiz</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    if (questions == null) questions = new ArrayList<Question>();
    if (quiz == null) { response.sendRedirect(request.getContextPath() + "/dashboard"); return; }
%>

<main class="container page">

  <div class="quiz-header-card">
    <div class="quiz-header-info">
      <h1><%=quiz.getTitle()%></h1>
      <p><%=quiz.getDescription()==null?"":quiz.getDescription()%></p>
<%  if (!questions.isEmpty()) { %>
      <div class="quiz-progress-track"><div class="quiz-progress-fill" id="quizProgressFill"></div></div>
      <p id="quizProgressLabel" style="margin-top:8px;font-weight:600;color:var(--color-text)">Question 1 of <%=questions.size()%></p>
<%  } %>
    </div>
<%  if (!questions.isEmpty()) { %>
    <div class="timer-chip" id="quizTimer" data-duration-min="<%=quiz.getDuration()%>">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg>
      <span id="quizTimerText"><%=quiz.getDuration()%>:00</span>
    </div>
<%  } %>
  </div>

  <form class="quiz-attempt-form" method="post" action="${pageContext.request.contextPath}/submitQuiz">
  <input type="hidden" name="quizId" value="<%=quiz.getQuizId()%>">

<%  if (questions.isEmpty()) { %>

  <div class="alert warning">
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
    This quiz has no questions yet.
  </div>

<%  } else { %>

  <div class="quiz-shell">
    <div class="quiz-questions">
<%
      int no = 1;
      for (Question q : questions) {
%>
      <div class="question-card">
        <div class="q-index">Question <%=no++%> <span class="marks-tag"><%=q.getMarks()%> mark<%=q.getMarks()==1?"":"s"%></span></div>
        <h3><%=q.getQuestionText()%></h3>
<%
        for (Option o : q.getOptions()) {
%>
        <div class="option">
          <label><input type="radio" name="q_<%=q.getQuestionId()%>" value="<%=o.getOptionId()%>"> <%=o.getOptionText()%></label>
        </div>
<%
        }
%>
      </div>
<%
      }
%>
      <div class="quiz-nav-actions">
        <button type="button" id="quizPrevBtn" class="btn btn-secondary" disabled>Previous</button>
        <button type="button" id="quizNextBtn" class="btn btn-secondary">Next</button>
        <button class="btn btn-success" type="submit">Submit Quiz</button>
      </div>
    </div>

    <aside class="quiz-sidebar">
      <div class="palette-card">
        <h4>Question Palette</h4>
        <div class="palette-grid" id="paletteGrid"></div>
        <div class="palette-legend">
          <span><span class="legend-dot current"></span> Current question</span>
          <span><span class="legend-dot answered"></span> Answered</span>
          <span><span class="legend-dot"></span> Not answered</span>
        </div>
      </div>
    </aside>
  </div>

<%  } %>

  </form>
</main>

<%  if (!questions.isEmpty()) { %>
<div class="modal-overlay" id="submitModal">
  <div class="modal-card">
    <h3>Submit Quiz?</h3>
    <p id="submitModalText">You still have unanswered questions.</p>
    <div class="modal-actions">
      <button type="button" class="btn btn-secondary" id="cancelSubmitBtn">Continue Quiz</button>
      <button type="button" class="btn btn-success" id="confirmSubmitBtn">Submit Quiz</button>
    </div>
  </div>
</div>
<%  } %>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
