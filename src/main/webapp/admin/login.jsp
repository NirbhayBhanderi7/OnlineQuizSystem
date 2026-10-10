<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Admin Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="auth-split">
  <div class="auth-visual">
    <h1>Quiz Admin Console</h1>
    <p>Manage quizzes and questions, review registered students, and monitor every attempt from one place.</p>
    <ul class="auth-visual-list">
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7" height="9" rx="1"/><rect x="14" y="3" width="7" height="5" rx="1"/><rect x="14" y="12" width="7" height="9" rx="1"/><rect x="3" y="16" width="7" height="5" rx="1"/></svg></span>Create and manage quizzes &amp; questions</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4v2"/><circle cx="10" cy="7" r="4"/></svg></span>View every registered student</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M3 3v18h18"/><rect x="7" y="12" width="3" height="6"/><rect x="12" y="8" width="3" height="10"/><rect x="17" y="5" width="3" height="13"/></svg></span>Track results across all attempts</li>
    </ul>
  </div>

  <div class="auth-form-side">
    <div class="form-card">
      <h2>Administrator Login</h2>
      <p class="form-sub">Restricted access &mdash; for quiz administrators only.</p>

      <% if(request.getAttribute("error")!=null){ %>
        <div class="alert">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
          <%=request.getAttribute("error")%>
        </div>
      <% } %>

      <form method="post" action="${pageContext.request.contextPath}/adminLogin">
        <div class="form-group">
          <label>Username</label>
          <input name="username" placeholder="admin" required>
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" name="password" placeholder="&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;&#8226;" required>
        </div>
        <button class="btn btn-block" type="submit">Login</button>
      </form>

      <p class="form-foot">Default: <b>admin</b> / <b>admin123</b></p>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
