<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("student") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<%@ page import="com.onlinequiz.model.Result" %>
<!DOCTYPE html>
<html>
<head>
<title>Result</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    Result r = (Result) request.getAttribute("result");
    if (r == null) { response.sendRedirect(request.getContextPath() + "/dashboard"); return; }
    int __pct = r.getTotalMarks() > 0 ? Math.round((r.getScore() * 100f) / r.getTotalMarks()) : 0;
%>

<main class="container page">
  <div class="card result-hero">
    <span class="badge badge-primary" style="margin-bottom:14px">Quiz Completed</span>
    <h1><%=r.getQuizTitle()%></h1>
    <p style="color:var(--color-text-muted);margin-top:6px">Student: <b style="color:var(--color-text)"><%=r.getStudentName()%></b></p>

    <div class="score-ring" style="--pct:<%=__pct%>">
      <div class="score-ring-inner">
        <span class="score-ring-value"><%=r.getScore()%>/<%=r.getTotalMarks()%></span>
        <span class="score-ring-label"><%=__pct%>%</span>
      </div>
    </div>

    <div class="result-stat-grid">
      <div class="result-stat">
        <div class="val" style="color:var(--color-success)"><%=r.getCorrectAnswers()%></div>
        <div class="lbl">Correct Answers</div>
      </div>
      <div class="result-stat">
        <div class="val" style="color:var(--color-danger)"><%=r.getWrongAnswers()%></div>
        <div class="lbl">Wrong Answers</div>
      </div>
      <div class="result-stat">
        <div class="val"><%=r.getTotalMarks()%></div>
        <div class="lbl">Total Marks</div>
      </div>
    </div>

    <div class="btn-row" style="justify-content:center">
      <a class="btn" href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a>
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/leaderboard?quizId=<%=r.getQuizId()%>">View Leaderboard</a>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
