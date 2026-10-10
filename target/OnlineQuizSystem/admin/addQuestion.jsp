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
<title>Add Question</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    String quizId = request.getParameter("quizId");
    if (quizId == null) quizId = (String) request.getAttribute("quizId");
    if (quizId == null) quizId = "";
%>

<main class="container page" style="max-width:640px">
  <div class="form-card" style="max-width:100%">
    <h2>Add Question</h2>
    <p class="form-sub">Adding to Quiz #<%=quizId.isEmpty() ? "?" : quizId%>. Fill in the question, its four options and mark the correct one.</p>

    <% if(request.getParameter("success")!=null){ %>
      <div class="alert success">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><path d="m9 11 3 3L22 4"/></svg>
        Question added successfully.
      </div>
    <% } %>
    <% if(request.getAttribute("error")!=null){ %>
      <div class="alert">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z"/><path d="M12 9v4M12 17h.01"/></svg>
        <%=request.getAttribute("error")%>
      </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/addQuestion">
      <div class="field-row">
        <div class="form-group">
          <label>Quiz ID</label>
          <input type="number" name="quizId" value="<%=quizId%>" required>
        </div>
        <div class="form-group">
          <label>Marks</label>
          <input type="number" name="marks" value="1" min="1" required>
        </div>
      </div>

      <div class="form-group">
        <label>Question</label>
        <textarea name="questionText" placeholder="Type the question text" required></textarea>
      </div>

      <div class="form-group">
        <label>Option 1</label>
        <input name="option1" required>
      </div>
      <div class="form-group">
        <label>Option 2</label>
        <input name="option2" required>
      </div>
      <div class="form-group">
        <label>Option 3</label>
        <input name="option3" required>
      </div>
      <div class="form-group">
        <label>Option 4</label>
        <input name="option4" required>
      </div>

      <div class="form-group">
        <label>Correct Option</label>
        <select name="correct">
          <option value="0">Option 1</option>
          <option value="1">Option 2</option>
          <option value="2">Option 3</option>
          <option value="3">Option 4</option>
        </select>
      </div>

      <button class="btn btn-success btn-block" type="submit">Add Question</button>
    </form>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
