<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
%>

<%@ page import="java.util.*,com.onlinequiz.model.Student" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Students</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>

        <%
            List<Student> list = (List<Student>) request.getAttribute("students");
            if (list == null) {
                list = new ArrayList<Student>();
            }
        %>

        <main class="container page">
            <div class="table-toolbar">
                <div class="page-head" style="margin-bottom:0">
                    <h1>Registered Students</h1>
                    <p><%=list.size()%> student<%=list.size() == 1 ? "" : "s"%> registered</p>
                </div>
                <div class="search-box">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>
                    <input type="text" placeholder="Search students&hellip;" data-filter-target=".table-container" data-filter-item="tbody tr">
                </div>
            </div>

            <% if (request.getParameter("deleted") != null) { %><div class="alert success">Student deleted successfully.</div><% } %>

            <div class="table-container">
                <div class="table-scroll">
                    <table>
                        <thead>
                            <tr><th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Action</th></tr>
                        </thead>
                        <tbody>
                            <%
                                for (Student s : list) {
                            %>
                            <tr>
                                <td>#<%=s.getStudentId()%></td>
                                <td><b><%=s.getName()%></b></td>
                                <td><%=s.getEmail()%></td>
                                <td><%=s.getCourse() == null ? "" : s.getCourse()%></td>
                                <td><form method="post" action="${pageContext.request.contextPath}/deleteStudent" onsubmit="return confirm('Delete this student and their results?');"><input type="hidden" name="studentId" value="<%=s.getStudentId()%>"><button class="btn btn-sm btn-danger" type="submit">Delete</button></form></td>
                            </tr>
                            <%
                                }
                                if (list.isEmpty()) {
                            %>
                            <tr><td colspan="5" class="table-empty">No students registered yet.</td></tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
