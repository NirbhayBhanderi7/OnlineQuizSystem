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
    <p class="form-sub">Set up the quiz, then add questions. Total time is calculated automatically from the number of questions.</p>
    <% if(request.getAttribute("error")!=null){ %><div class="alert"><%=request.getAttribute("error")%></div><% } %>
    <form method="post" action="${pageContext.request.contextPath}/createQuiz">
      <div class="form-group">
        <label for="title">Quiz Title</label>
        <input id="title" name="title" placeholder="e.g. Java Fundamentals" required maxlength="150">
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea id="description" name="description" placeholder="What does this quiz cover?"></textarea>
      </div>
      <div class="form-group">
        <label for="timePerQuestionMinutes">Time per Question (minutes) *</label>
        <input id="timePerQuestionMinutes" type="number" name="timePerQuestionMinutes" min="1" max="180" value="1" required>
        <small>Default: 1 minute per question. The total quiz duration will be question count × this value.</small>
      </div>
      <button class="btn btn-block" type="submit">Create Quiz &amp; Add Questions</button>
    </form>
  </div>
</main>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
