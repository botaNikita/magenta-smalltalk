package ru.magentasmalltalk.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("login") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null && login.equals("test") && password.equals("123")) {
            req.getSession().setAttribute("login", login);
            resp.sendRedirect(req.getContextPath());
        } else {
            resp.sendRedirect("login?login=" + login);
        }
    }
}
