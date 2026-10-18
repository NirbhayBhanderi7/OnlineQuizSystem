<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.Result" %>
<!DOCTYPE html>
<html>
<head>
<title>Results</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    List<Result> list = (List<Result>) request.getAttribute("results");
    if (list == null) {
        list = new ArrayList<Result>();
    }
%>

<main class="container page">
  <div class="table-toolbar">
    <div class="page-head" style="margin-bottom:0">
      <h1>Quiz Results</h1>
      <p><%=list.size()%> attempt<%=list.size()==1?"":"s"%> recorded</p>
    </div>
    <div class="search-box">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>
      <input type="text" placeholder="Search results&hellip;" data-filter-target=".table-container" data-filter-item="tbody tr">
    </div>
  </div>

  <div class="table-container">
    <div class="table-scroll">
      <table>
        <thead>
        <tr><th>Student</th><th>Quiz</th><th>Score</th><th>Correct</th><th>Wrong</th><th>Attempted</th></tr>
        </thead>
        <tbody>
<%
    for (Result r : list) {
%>
        <tr>
          <td><b><%=r.getStudentName()%></b></td>
          <td><%=r.getQuizTitle()%></td>
          <td><%=r.getScore()%>/<%=r.getTotalMarks()%></td>
          <td><span class="badge badge-success"><%=r.getCorrectAnswers()%></span></td>
          <td><span class="badge badge-danger"><%=r.getWrongAnswers()%></span></td>
          <td><%=r.getAttemptedAt()%></td>
        </tr>
<%
    }
    if (list.isEmpty()) {
%>
        <tr><td colspan="6" class="table-empty">No results recorded yet.</td></tr>
<%
    }
%>
        </tbody>
      </table>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
