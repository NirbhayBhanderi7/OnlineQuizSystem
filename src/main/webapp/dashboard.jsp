<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("student") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.Quiz" %>
<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");
    if (quizzes == null) quizzes = new ArrayList<Quiz>();
    int __totalQuestions = 0;
    for (Quiz __q : quizzes) { __totalQuestions += __q.getTotalQuestions(); }
%>

<main class="container page">
  <div class="page-head">
    <h1>Welcome back!</h1>
    <p>Choose a quiz below and start your examination.</p>
  </div>

  <div class="stat-grid">
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg>
      </div>
      <div>
        <div class="stat-value" data-count="<%=quizzes.size()%>">0</div>
        <div class="stat-label">Available Quizzes</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M9.5 9a2.5 2.5 0 1 1 3.5 2.3c-.9.4-1.5 1-1.5 2"/><path d="M12 17h.01"/></svg>
      </div>
      <div>
        <div class="stat-value" data-count="<%=__totalQuestions%>">0</div>
        <div class="stat-label">Total Questions</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 21h8M12 17v4"/><path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"/><path d="M7 5H4a3 3 0 0 0 3 3M17 5h3a3 3 0 0 1-3 3"/></svg>
      </div>
      <div>
        <div class="stat-value" style="font-size:16px;padding-top:4px"><a href="${pageContext.request.contextPath}/leaderboard" style="color:var(--color-primary)">View rankings</a></div>
        <div class="stat-label">Leaderboard</div>
      </div>
    </div>
  </div>

  <div class="table-toolbar">
    <h2 class="section-title" style="margin:0">Available Quizzes</h2>
    <div class="search-box">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>
      <input type="text" placeholder="Search quizzes&hellip;" data-filter-target=".card-grid" data-filter-item=".quiz-card">
    </div>
  </div>

  <div class="card-grid">
<%
    for (Quiz q : quizzes) {
%>
    <div class="quiz-card">
      <div class="quiz-card-top">
        <h3><%=q.getTitle()%></h3>
        <span class="quiz-tag">Quiz</span>
      </div>
      <p class="quiz-desc"><%=q.getDescription()==null?"":q.getDescription()%></p>
      <div class="quiz-meta">
        <span>
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg>
          <%=q.getDuration()%> minutes
        </span>
        <span>
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg>
          <%=q.getTotalQuestions()%> questions
        </span>
      </div>
      <div class="btn-row">
        <a class="btn" href="${pageContext.request.contextPath}/quiz?quizId=<%=q.getQuizId()%>">Start Quiz</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/leaderboard?quizId=<%=q.getQuizId()%>">Leaderboard</a>
      </div>
    </div>
<%
    }
    if (quizzes.isEmpty()) {
%>
    <div class="card empty-state" style="grid-column:1/-1">
      <svg class="empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg>
      <p>No quizzes available right now. Check back soon.</p>
    </div>
<%
    }
%>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
