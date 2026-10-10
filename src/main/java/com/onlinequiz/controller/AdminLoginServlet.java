package com.onlinequiz.controller;
import com.onlinequiz.model.Admin;
import com.onlinequiz.service.AdminService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            Admin a=new AdminService().login(req.getParameter("username"),req.getParameter("password"));
            if(a!=null){req.getSession().setAttribute("admin",a);resp.sendRedirect(req.getContextPath()+"/adminDashboard");}
            else{req.setAttribute("error","Invalid admin username or password.");req.getRequestDispatcher("/admin/login.jsp").forward(req,resp);}
        }catch(Exception e){req.setAttribute("error","Database error. Check MySQL and DBConnection settings.");req.getRequestDispatcher("/admin/login.jsp").forward(req,resp);}
    }
}
