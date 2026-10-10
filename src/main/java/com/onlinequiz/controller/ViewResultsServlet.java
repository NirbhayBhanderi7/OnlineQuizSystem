package com.onlinequiz.controller;
import com.onlinequiz.service.ResultService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/viewResults")
public class ViewResultsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(req.getSession().getAttribute("admin")==null){resp.sendRedirect(req.getContextPath()+"/admin/login.jsp");return;}
        try{req.setAttribute("results",new ResultService().findAll());req.getRequestDispatcher("/admin/viewResults.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}
    }
}
