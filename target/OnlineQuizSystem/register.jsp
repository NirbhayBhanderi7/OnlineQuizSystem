<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="auth-split">
  <div class="auth-visual">
    <h1>Create your student account</h1>
    <p>Sign up in a minute to start attempting quizzes, tracking your scores and competing on the leaderboard.</p>
    <ul class="auth-visual-list">
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4v2"/><circle cx="10" cy="7" r="4"/></svg></span>Free to join, no approval required</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 3"/></svg></span>Quizzes are timed and self-paced</li>
      <li><span class="bullet"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg></span>Already have an account? Just log in</li>
    </ul>
  </div>

  <div class="auth-form-side">
    <div class="form-card">
      <h2>Create Student Account</h2>
      <p class="form-sub">Fill in your details below to get started.</p>

      <% if(request.getAttribute("error")!=null){ %>
        <div class="alert">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
          <%=request.getAttribute("error")%>
        </div>
      <% } %>

      <form method="post" action="${pageContext.request.contextPath}/register">
        <div class="form-group">
          <label>Name</label>
          <input name="name" placeholder="Your full name" required>
        </div>
        <div class="form-group">
          <label>Email</label>
          <input type="email" name="email" placeholder="you@example.com" required>
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" name="password" placeholder="Create a password" required>
        </div>
        <div class="form-group">
          <label>Course</label>
          <input name="course" placeholder="e.g. B.Tech Computer Science">
        </div>
        <button class="btn btn-block" type="submit">Register</button>
      </form>

      <p class="form-foot">Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Login here</a></p>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
