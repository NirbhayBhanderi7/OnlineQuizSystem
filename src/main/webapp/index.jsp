<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Online Quiz System</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="container page">
  <div class="hero">
    <div class="hero-inner">
      <span class="hero-eyebrow">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" width="15" height="15"><circle cx="12" cy="12" r="9"/><path d="M9.5 9a2.5 2.5 0 1 1 3.5 2.3c-.9.4-1.5 1-1.5 2"/><path d="M12 17h.01"/></svg>
        Online Examination Platform
      </span>
      <h1>Take quizzes, track your scores, and see how you rank.</h1>
      <p>A simple, focused way to practice and be tested online &mdash; instant results, question-wise feedback and a live leaderboard for every quiz.</p>
      <div class="btn-row">
        <a class="btn btn-outline" style="background:#fff;color:var(--color-primary);border-color:#fff" href="${pageContext.request.contextPath}/login.jsp">Student Login</a>
        <a class="btn btn-outline" href="${pageContext.request.contextPath}/register.jsp">Register</a>
        <a class="btn btn-outline" href="${pageContext.request.contextPath}/admin/login.jsp">Admin Login</a>
      </div>
    </div>
  </div>

  <h2 class="section-title">Built for a smooth testing experience</h2>
  <div class="card-grid">
    <div class="card">
      <div class="stat-icon" style="margin-bottom:12px">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg>
      </div>
      <h3>Multiple-choice quizzes</h3>
      <p style="color:var(--color-text-muted);font-size:14px;margin-top:6px">Attempt quizzes through a clean, distraction-free interface with a live question palette.</p>
    </div>
    <div class="card">
      <div class="stat-icon" style="margin-bottom:12px">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg>
      </div>
      <h3>Instant results</h3>
      <p style="color:var(--color-text-muted);font-size:14px;margin-top:6px">See your score, correct answers and wrong answers the moment you submit.</p>
    </div>
    <div class="card">
      <div class="stat-icon" style="margin-bottom:12px">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 21h8M12 17v4"/><path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"/><path d="M7 5H4a3 3 0 0 0 3 3M17 5h3a3 3 0 0 1-3 3"/></svg>
      </div>
      <h3>Leaderboard</h3>
      <p style="color:var(--color-text-muted);font-size:14px;margin-top:6px">Compare your ranking against every other student who has attempted a quiz.</p>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
