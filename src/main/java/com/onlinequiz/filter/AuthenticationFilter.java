package com.onlinequiz.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig config) { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.startsWith(context) ? uri.substring(context.length()) : uri;
        if (path.isEmpty()) path = "/";

        HttpSession session = req.getSession(false);
        boolean studentLoggedIn = session != null && session.getAttribute("student") != null;
        boolean adminLoggedIn = session != null && session.getAttribute("admin") != null;

        boolean adminPage = path.startsWith("/admin/") && !"/admin/login.jsp".equals(path);
        boolean adminAction = "/adminDashboard".equals(path)
                || "/createQuiz".equals(path) || "/addQuestion".equals(path)
                || "/updateQuiz".equals(path) || "/deleteQuiz".equals(path)
                || "/deleteStudent".equals(path) || "/viewStudents".equals(path)
                || "/viewResults".equals(path) || "/manageQuestions".equals(path)
                || "/updateQuestion".equals(path);
        boolean studentPage = "/dashboard".equals(path) || "/dashboard.jsp".equals(path)
                || "/quiz".equals(path) || "/quiz.jsp".equals(path)
                || "/submitQuiz".equals(path) || "/leaderboard".equals(path)
                || "/leaderboard.jsp".equals(path) || "/result.jsp".equals(path);

        if (adminPage || adminAction || studentPage || "/logout".equals(path)) {
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);
        }

        if (adminPage || adminAction) {
            if (!adminLoggedIn || studentLoggedIn) {
                if (session != null && studentLoggedIn) session.removeAttribute("student");
                resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
                return;
            }
        } else if (studentPage) {
            if (!studentLoggedIn || adminLoggedIn) {
                if (session != null && adminLoggedIn) session.removeAttribute("admin");
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}
