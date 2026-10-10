<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.Quiz" %>
<!DOCTYPE html>
<html>
<head>
<title>Admin Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="container page">
  <div class="page-head">
    <h1>Admin Dashboard</h1>
    <p>Manage quizzes, questions, students and results from one place.</p>
  </div>

  <div class="stat-grid">
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H7a4 4 0 0 0-4 4v2"/><circle cx="10" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
      </div>
      <div>
        <div class="stat-value" data-count="<%=request.getAttribute("studentCount")%>">0</div>
        <div class="stat-label">Students</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M8 6h13M8 12h13M8 18h13"/><path d="M3 6h.01M3 12h.01M3 18h.01"/></svg>
      </div>
      <div>
        <div class="stat-value" data-count="<%=request.getAttribute("quizCount")%>">0</div>
        <div class="stat-label">Quizzes</div>
      </div>
    </div>
    <div class="stat-card">
      <div class="stat-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M3 3v18h18"/><rect x="7" y="12" width="3" height="6"/><rect x="12" y="8" width="3" height="10"/><rect x="17" y="5" width="3" height="13"/></svg>
      </div>
      <div>
        <div class="stat-value" data-count="<%=request.getAttribute("resultCount")%>">0</div>
        <div class="stat-label">Results</div>
      </div>
    </div>
  </div>

  <% if(request.getParameter("updated")!=null){ %><div class="alert success">Quiz updated successfully.</div><% } %>
  <% if(request.getParameter("deleted")!=null){ %><div class="alert success">Record deleted successfully.</div><% } %>
  <% if(request.getAttribute("error")!=null){ %><div class="alert"><%=request.getAttribute("error")%></div><% } %>

  <div class="btn-row" style="margin-bottom:34px">
    <a class="btn" href="${pageContext.request.contextPath}/admin/createQuiz.jsp">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" width="16" height="16"><path d="M12 5v14M5 12h14"/></svg>
      Create Quiz
    </a>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/viewStudents">View Students</a>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/viewResults">View Results</a>
  </div>

  <div id="quizzes" class="table-toolbar">
    <h2 class="section-title" style="margin:0">Quizzes</h2>
    <div class="search-box">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>
      <input type="text" placeholder="Search quizzes&hellip;" data-filter-target=".table-container" data-filter-item="tbody tr">
    </div>
  </div>

  <div class="table-container">
    <div class="table-scroll">
      <table>
        <thead>
        <tr><th>ID</th><th>Title</th><th>Duration</th><th>Questions</th><th>Action</th></tr>
        </thead>
        <tbody>
<%
    List<Quiz> quizzes = (List<Quiz>) request.getAttribute("quizzes");
    if (quizzes == null) quizzes = new ArrayList<Quiz>();
    for (Quiz q : quizzes) {
%>
        <tr>
          <td>#<%=q.getQuizId()%></td>
          <td><b><%=q.getTitle()%></b></td>
          <td><%=q.getDuration()%> min</td>
          <td><%=q.getTotalQuestions()%></td>
          <td><a class="btn btn-sm" href="${pageContext.request.contextPath}/admin/addQuestion.jsp?quizId=<%=q.getQuizId()%>">Add Questions</a>
            <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/admin/editQuiz.jsp?quizId=<%=q.getQuizId()%>">Edit</a>
            <form method="post" action="${pageContext.request.contextPath}/deleteQuiz" style="display:inline" onsubmit="return confirm('Delete this quiz and all its questions/results?');"><input type="hidden" name="quizId" value="<%=q.getQuizId()%>"><button class="btn btn-sm btn-danger" type="submit">Delete</button></form></td>
        </tr>
<%
    }
    if (quizzes.isEmpty()) {
%>
        <tr><td colspan="5" class="table-empty">No quizzes created yet.</td></tr>
<%
    }
%>
        </tbody>
      </table>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
