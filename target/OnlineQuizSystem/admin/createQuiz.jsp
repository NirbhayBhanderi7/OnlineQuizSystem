<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<title>Create Quiz</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="container page" style="max-width:640px">
  <div class="form-card" style="max-width:100%">
    <h2>Create New Quiz</h2>
    <p class="form-sub">Set up the quiz details, then add questions to it.</p>

    <% if(request.getAttribute("error")!=null){ %>
      <div class="alert">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
        <%=request.getAttribute("error")%>
      </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/createQuiz">
      <div class="form-group">
        <label>Quiz Title</label>
        <input name="title" placeholder="e.g. Java Fundamentals" required>
      </div>
      <div class="form-group">
        <label>Description</label>
        <textarea name="description" placeholder="What does this quiz cover?"></textarea>
      </div>
      <div class="form-group">
        <label>Duration (minutes)</label>
        <input type="number" name="duration" min="1" placeholder="30" required>
      </div>
      <button class="btn btn-block" type="submit">Create Quiz &amp; Add Questions</button>
    </form>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
