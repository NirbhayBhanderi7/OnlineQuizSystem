package com.onlinequiz.controller;

import com.onlinequiz.service.StudentService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        try {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            new StudentService().delete(studentId);
            resp.sendRedirect(req.getContextPath() + "/viewStudents?deleted=1");
        } catch (Exception e) {
            throw new ServletException("Unable to delete student.", e);
        }
    }
}
