<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("student") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.Leaderboard" %>
<!DOCTYPE html>
<html>
<head>
<title>Leaderboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    List<Leaderboard> list = (List<Leaderboard>) request.getAttribute("leaderboard");
    if (list == null) list = new ArrayList<Leaderboard>();
%>

<main class="container page">
  <div class="page-head">
    <h1>Leaderboard</h1>
    <p>See how your score compares with everyone else who attempted this quiz.</p>
  </div>

  <div class="table-container">
    <div class="table-scroll">
      <table>
        <tr><th>Rank</th><th>Student</th><th>Score</th></tr>
<%
        for (Leaderboard l : list) {
          String __rankClass = l.getRankPosition() == 1 ? "rank-1" : l.getRankPosition() == 2 ? "rank-2" : l.getRankPosition() == 3 ? "rank-3" : "";
%>
        <tr>
          <td><span class="rank-badge <%=__rankClass%>">#<%=l.getRankPosition()%></span></td>
          <td><%=l.getStudentName()%></td>
          <td><b><%=l.getScore()%></b></td>
        </tr>
<%
        }
        if (list.isEmpty()) {
%>
        <tr><td colspan="3" class="table-empty">No results yet.</td></tr>
<%
        }
%>
      </table>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
