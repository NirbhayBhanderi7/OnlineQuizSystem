<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.onlinequiz.model.Quiz" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<title>Edit Quiz</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    if (quiz == null) {
        String id = request.getParameter("quizId");
        if (id != null) {
            try { quiz = new com.onlinequiz.service.QuizService().find(Integer.parseInt(id)); }
            catch (Exception ignored) { }
        }
    }
    if (quiz == null) {
%>
<main class="container page"><div class="alert">Quiz not found.</div></main>
<% } else { %>
<main class="container page" style="max-width:640px">
  <div class="form-card" style="max-width:100%">
    <h2>Edit Quiz</h2>
    <p class="form-sub">Update the title, description or duration of this quiz.</p>
    <% if(request.getAttribute("error")!=null){ %><div class="alert"><%=request.getAttribute("error")%></div><% } %>
    <form method="post" action="${pageContext.request.contextPath}/updateQuiz">
      <input type="hidden" name="quizId" value="<%=quiz.getQuizId()%>">
      <div class="form-group"><label>Quiz Title</label><input name="title" value="<%=quiz.getTitle()%>" required></div>
      <div class="form-group"><label>Description</label><textarea name="description"><%=quiz.getDescription()==null?"":quiz.getDescription()%></textarea></div>
      <div class="form-group"><label>Duration (minutes)</label><input type="number" name="duration" min="1" value="<%=quiz.getDuration()%>" required></div>
      <div class="btn-row"><button class="btn" type="submit">Save Changes</button><a class="btn btn-secondary" href="${pageContext.request.contextPath}/adminDashboard">Cancel</a></div>
    </form>
  </div>
</main>
<% } %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>