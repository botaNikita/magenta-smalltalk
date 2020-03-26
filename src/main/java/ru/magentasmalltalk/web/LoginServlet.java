package ru.magentasmalltalk.web;

import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.persistence.EntityManager;
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
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        req.getRequestDispatcher("/pages/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null) {
            if (password != null) {

                EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
                User user;

                try {
                    UsersDAO usersDAO = new UsersDAO(manager);
                    user = usersDAO.findUserByLogin(login);
                } finally {
                    manager.close();
                }

                if (user == null || !password.equals(user.getPassword())) {
                    resp.sendRedirect("login?login=" + login);
                    return;
                }

                req.getSession().setAttribute("userName", user.getName());
                req.getSession().setAttribute("userId", user.getId());
                req.getSession().setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);
                resp.sendRedirect(req.getContextPath());

            } else {
                resp.sendRedirect("login?login=" + login);
            }
        } else {
            resp.sendRedirect("login");
        }
    }
}
