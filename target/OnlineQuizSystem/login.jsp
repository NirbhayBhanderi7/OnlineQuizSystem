<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Student Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="auth-split">
  <div class="auth-visual">
    <h1>Welcome back to Online Quiz System</h1>
    <p>Log in to pick up where you left off, attempt a new quiz, and check how you stack up on the leaderboard.</p>
    <ul class="auth-visual-list">
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg></span>Attempt quizzes across every subject you're enrolled in</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg></span>Get your score and answer breakdown instantly</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 21h8M12 17v4"/><path d="M7 4h10v5a5 5 0 0 1-10 0V4Z"/><path d="M7 5H4a3 3 0 0 0 3 3M17 5h3a3 3 0 0 1-3 3"/></svg></span>Track your rank on the live leaderboard</li>
    </ul>
  </div>

  <div class="auth-form-side">
    <div class="form-card">
      <h2>Student Login</h2>
      <p class="form-sub">Enter your details to access your dashboard.</p>

      <% if(request.getParameter("registered")!=null){ %>
        <div class="alert success">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg>
          Registration successful. Please login.
        </div>
      <% } %>
      <% if(request.getAttribute("error")!=null){ %>
        <div class="alert">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
          <%=request.getAttribute("error")%>
        </div>
      <% } %>

      <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
          <label>Email</label>
          <input type="email" name="email" placeholder="you@example.com" required>
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" name="password" placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;" required>
        </div>
        <button class="btn btn-block" type="submit">Login</button>
      </form>

      <p class="form-foot">New student? <a href="${pageContext.request.contextPath}/register.jsp">Register here</a></p>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
